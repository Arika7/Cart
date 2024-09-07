package com.example.shoppingcart.config;


import com.example.shoppingcart.models.Cart;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private Integer port;


    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {

        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();

        configuration.setHostName(host);
        configuration.setPort(port);

        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public CacheManager cacheManager() {
        RedisCacheConfiguration days3 = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofDays(3));

        return RedisCacheManager.builder(redisConnectionFactory()).withCacheConfiguration("cart", days3).build();
    }

    @Bean
    public RedisTemplate<Long, Cart> redisTemplate() {
        final RedisTemplate<Long, Cart> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        var jacksonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        template.setValueSerializer(jacksonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}