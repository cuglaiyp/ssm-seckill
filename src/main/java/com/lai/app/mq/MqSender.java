package com.lai.app.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqSender {
    @Autowired
    AmqpTemplate amqpTemplate;

    public void sendMessage(){
        amqpTemplate.convertAndSend("ex","msg","hello");
        amqpTemplate.convertAndSend("seckill1","hello world");
    }

}
