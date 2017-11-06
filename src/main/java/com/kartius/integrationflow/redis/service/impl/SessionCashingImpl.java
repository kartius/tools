package com.kartius.integrationflow.redis.service.impl;

import com.kartius.integrationflow.redis.service.SessionCashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@EnableCaching
@Service
public class SessionCashingImpl implements SessionCashing {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void save(Object o) {
        redisTemplate.opsForValue().set( o.toString(), o);
    }

    public Object find(String id) {
        return redisTemplate.opsForValue().get(id);
    }
}
