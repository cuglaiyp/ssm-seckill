package com.lai.app.dao;

import com.lai.app.config.SpringConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class SuccessKillDaoTest {

    @Autowired
    SuccessKillDao successKillDao;
    @Test
    public void insertSuccessKilled() {
        successKillDao.insertSuccessKilled(1000, 15623014301l);
    }

    @Test
    public void queryByIdWithSeckill() {
        System.out.println(successKillDao.queryByIdWithSeckill(1000));
    }
}