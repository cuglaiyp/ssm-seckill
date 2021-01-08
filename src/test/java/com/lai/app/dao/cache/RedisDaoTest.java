package com.lai.app.dao.cache;

import com.lai.app.config.SpringConfig;
import com.lai.app.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class RedisDaoTest {

    @Autowired
    private RedisDao redisDao;

    @Test
    public void getSeckill() {
        Seckill seckill = redisDao.getSeckill(1000);
        if (seckill == null) {
        }
    }


    @Test
    public void putSeckill() {
    }
}