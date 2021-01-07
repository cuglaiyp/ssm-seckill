package com.lai.app.service;

import com.lai.app.dto.Exposer;
import com.lai.app.dto.SeckillExecution;
import com.lai.app.entity.Seckill;
import com.lai.app.exception.RepeatKillException;
import com.lai.app.exception.SeckillCloseException;
import com.lai.app.exception.SeckillException;

import java.util.List;

/**
 * 业务接口： 站在使用者角度设计接口
 * 考虑三个方面：1、方法定义粒度
 *             2、参数
 *             3、返回类型
 */
public interface SeckillService {
    /**
     * 查询所有秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启时输出秒杀接口的地址
     * 否则输出是系统时间和秒杀时间
     * @param seckillId
     * @return
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 秒杀执行
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws RepeatKillException
     * @throws SeckillCloseException
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException;
}

