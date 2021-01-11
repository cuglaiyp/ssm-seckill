package com.lai.app.service.impl;

import com.lai.app.dao.SeckillDao;
import com.lai.app.dao.SuccessKillDao;
import com.lai.app.dao.cache.RedisDao;
import com.lai.app.dto.Exposer;
import com.lai.app.dto.SeckillExecution;
import com.lai.app.entity.Seckill;
import com.lai.app.entity.SuccessKilled;
import com.lai.app.enums.SeckillStateEnum;
import com.lai.app.exception.RepeatKillException;
import com.lai.app.exception.SeckillCloseException;
import com.lai.app.exception.SeckillException;
import com.lai.app.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String slat = "sdiuegriuhfbgjjbfpwegbg'";

    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKillDao successKillDao;
    @Autowired
    private RedisDao redisDao;

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryBySeckillId(seckillId);
    }


    /**
     * 因为内部方法是不走AOP的，所以直接用exportSeckillUrl调用这个方法不行。
     * 解决方法：将这个方法放到redisDao里面去
     * @param seckillId
     * @return
     */
/*    @Cacheable(value = "simpleCache", key = "#seckillId")
    public Seckill getSeckill(long seckillId) {
        System.out.println("无缓存");
        return seckillDao.queryBySeckillId(seckillId);
    }*/

    /**
     * 暴露秒杀链接
     *
     * @param seckillId
     * @return
     */
    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        /*缓存优化*/
        // 1.访问redis
        Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null) {
            return Exposer.builder()
                    .exposed(false)
                    .seckillId(seckillId)
                    .build();
        }



/*        Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null) {
            // 2. 访问数据库
            seckill = seckillDao.queryBySeckillId(seckillId);
            if (seckill == null) {
                return Exposer.builder()
                        .exposed(false)
                        .seckillId(seckillId)
                        .build();
            }else {
                // 3. 放入redis
                redisDao.putSeckill(seckill);
            }
        }*/

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date curTime = new Date();
        if (curTime.getTime() < startTime.getTime() || curTime.getTime() > endTime.getTime()) {
            return Exposer.builder()
                    .exposed(false)
                    .seckillId(seckillId)
                    .now(curTime.getTime())
                    .start(startTime.getTime())
                    .end(endTime.getTime())
                    .build();
        }
        String md5 = getMD5(seckillId);
        return Exposer.builder()
                .exposed(true)
                .seckillId(seckillId)
                .mds(md5).build();
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

    /**
     * 秒杀方法
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws RepeatKillException
     * @throws SeckillCloseException
     */
    @Transactional
    @Override
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if (md5 == null || md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }
        // 执行秒杀逻辑：减库存 + 记录购买行为
        Date nowTime = new Date();
        try {
            // 减库存成功
            int inserCount = successKillDao.insertSuccessKilled(seckillId, userPhone);
            if (inserCount <= 0) {
                // 重复秒杀
                throw new RepeatKillException("seckill repeated");
            } else {
                int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
                if (updateCount <= 0) {
                    // 没有更新成功
                    throw new SeckillCloseException("seckill is closed");
                } else {
                    SuccessKilled successKilled = successKillDao.queryByIdWithSeckill(seckillId);
                    return SeckillExecution.builder()
                            .seckillId(seckillId)
                            .seckillStateEnum(SeckillStateEnum.SUCCESS)
                            .successKilled(successKilled)
                            .build();
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            // 所有编译期异常转化为运行期异常 让spring声明式事务能够感知并rollback
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }
    }
}
