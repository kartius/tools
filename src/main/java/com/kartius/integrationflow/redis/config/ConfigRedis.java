package com.kartius.integrationflow.redis.config;

import com.kartius.integrationflow.redis.model.CustomData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class ConfigRedis extends CachingConfigurerSupport {

    @Value("${redis.url}")
    private String redisUrl;
    @Value("${redis.port}")
    private String redisPort;
    @Value("${redis.expiration}")
    private String redisExpiration;

    public String getRedisUrl() {
        return redisUrl;
    }

    public void setRedisUrl(String redisUrl) {
        this.redisUrl = redisUrl;
    }

    public String getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(String redisPort) {
        this.redisPort = redisPort;
    }

    public String getRedisExpiration() {
        return redisExpiration;
    }

    public void setRedisExpiration(String redisExpiration) {
        this.redisExpiration = redisExpiration;
    }


    @Bean(name = "serviceKeyGenerator")
    public ServiceKeyGenerator getServiceKeyGenerator() {
        return new ServiceKeyGenerator();
    }

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();

        // Defaults
        redisConnectionFactory.setHostName(redisUrl);
        redisConnectionFactory.setPort(Integer.valueOf(redisPort));
        return redisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory cf) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(cf);
        return redisTemplate;
    }

    @Bean(name = "redisTemplateSession")
    public RedisTemplate<String, CustomData> redisTemplateSession(RedisConnectionFactory cf) {
        RedisTemplate<String, CustomData> redisTemplate = new RedisTemplate<String, CustomData>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(cf);
        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        // Number of seconds before expiration. Defaults to unlimited (0)
        cacheManager.setDefaultExpiration(Integer.valueOf(redisExpiration));
        return cacheManager;
    }
}
