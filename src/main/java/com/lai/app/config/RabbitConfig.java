package com.lai.app.config;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfig {

    @Bean
    public ConnectionFactory amqpConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("localhost:5672");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/quest");
        //connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);
        //connectionFactory.setChannelCacheSize(25);
        //connectionFactory.setChannelCheckoutTimeout(0);
        //connectionFactory.setPublisherReturns(false);
        //connectionFactory.setPublisherConfirms(false);
        //connectionFactory.addConnectionListener(connectionListener);
        //connectionFactory.addChannelListener(channelListener);
        //connectionFactory.setRecoveryListener(recoveryListener);
        //connectionFactory.setConnectionCacheSize(1);
        //connectionFactory.setConnectionLimit(Integer.MAX_VALUE);
        return connectionFactory;
    }

    /**
     * 由于配置了自定义的amqpConnectionFactory，所以必须配置这个管理，不然就报找不到队列的异常
     * @param amqpConnectionFactory
     * @return
     */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory amqpConnectionFactory){
        RabbitAdmin rabbitAdmin=new RabbitAdmin(amqpConnectionFactory);
        return rabbitAdmin;
    }

    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        //SimpleRabbitListenerContainerFactory发现消息中有content_type有text就会默认将其转换成string类型的
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //TODO:可以设置消息转换器
        return factory;
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        AmqpTemplate amqpTemplate = new RabbitTemplate(connectionFactory);
        return amqpTemplate;
    }

    @Bean
    public Queue queue(){
        Queue queue = new Queue("seckill1",true,false,false);
        return queue;
    }

/*    @Bean
    public Exchange exchange(){
        Exchange exchange = new DirectExchange("ex");
        return exchange;
    }*/
}
