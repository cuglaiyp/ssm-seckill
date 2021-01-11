package com.lai.app.config;

import com.lai.app.entity.Seckill;
import com.lai.app.util.ProtostuffSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@PropertySource("classpath:redis.properties")
@EnableCaching
public class CacheConfig {


    @Value("${host}")
    private String host;
    @Value("${port}")
    private int port;
    @Value("${maxTotal}")
    private int maxTotal;
    @Value("${maxIdle}")
    private int maxIdle;

/*    @Bean
    public JedisPool getJedisPool() {
        return new JedisPool(host, port);
    }*/

    // Redis缓存管理器
    @Bean
    public CacheManager cacheManager(RedisTemplate template) {
        return new RedisCacheManager(template);
    }


    // RedisTemplate
    @Bean
    public RedisTemplate<Long, Seckill> redisTemplate(RedisConnectionFactory redisCF) {
        RedisTemplate<Long, Seckill> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisCF);
        redisTemplate.setKeySerializer(new ProtostuffSerializer<>(Long.class));
        redisTemplate.setValueSerializer(new ProtostuffSerializer<>(Seckill.class));
        return redisTemplate;
    }

    // Redis连接工厂
    @Bean
    public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        JedisConnectionFactory cf = new JedisConnectionFactory();
        cf.setHostName(host);
        cf.setPort(port);
        cf.setUsePool(true);
        cf.setPoolConfig(jedisPoolConfig);
        return cf;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        return jedisPoolConfig;
    }
}
