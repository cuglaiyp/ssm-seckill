package com.lai.app.dao;

import com.lai.app.config.SpringConfig;
import com.lai.app.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class) // 让junit运行于spring环境，以便在测试开始时加下上下文
@ContextConfiguration(classes = {SpringConfig.class}) // spring的配置文件
public class SeckillDaoTest {

    @Autowired
    SeckillDao seckillDao;



    // 事务注解是有效的，但是要放到service上面，因为那里可以被spring扫描到。加在这个测试方法上面，其实也是有效的，但是默认是回滚的，也就是说不提交到数据库。
    @Test
    @Transactional
    @Rollback(value = false)
    public void reduceNumber() {
        long id = 1000l;
        System.out.println(seckillDao.reduceNumber(id, new Date()));
    }

    @Test
    public void queryById() {
        long id = 1000l;
        Seckill seckill = seckillDao.queryBySeckillId(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void queryAll() {
        List<Seckill> seckills = seckillDao.queryAll(2, 1000);
        seckills.forEach(e -> System.out.println(e));
    }
}