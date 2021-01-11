package com.lai.app.dao.cache;

import com.lai.app.dao.SeckillDao;
import com.lai.app.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;


@Repository
public class RedisDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private SeckillDao seckillDao;

/*    // 用来序列化的规则
    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    @Autowired
    private JedisPool jedisPool;



    public Seckill getSeckill(long seckillId){
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckillId;
                // redis内部并没有实现序列化
                // 采用自定义序列化
                byte[] bytes = jedis.get(key.getBytes());
                if (bytes != null){
                    Seckill seckill = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
                    return seckill;
                }
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public String putSeckill(Seckill seckill){
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckill.getSeckillId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill,schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                int timeout = 60 * 60;
                String result = jedis.setex(key.getBytes(), timeout, bytes);
                return result;
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return null;
    }*/


    // 注意key的El表达式，是将long转换成String
    @Cacheable(value = "simpleCache", /*key = "T(String).valueOf(#seckillId)"*/ key = "#seckillId")
    public Seckill getSeckill(long seckillId) {
        System.out.println("无缓存");
        return seckillDao.queryBySeckillId(seckillId);
    }
}
