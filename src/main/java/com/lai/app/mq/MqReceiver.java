package com.lai.app.mq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
public class MqReceiver {


    // 交换机直连模式
    @RabbitListener(
            bindings = @QueueBinding(
            value = @Queue("seckill"),
            exchange = @Exchange(value = "ex",type = "direct"),
            key = "msg")
    )
    public void receive(Message message){
        System.out.println("接受到消息");
        System.out.println(new String(message.getBody()));
    }

    @RabbitListener(
        queues = "seckill1"
    )
    public void receive1(Message message){
        System.out.println("接受到消息1");
        System.out.println(new String(message.getBody()));
    }
}
