package com.kartius.integrationflow.redis.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DataRepositoryImpl implements DataRepository {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(Object o) {
        redisTemplate.opsForValue().set( o.toString(), o);
    }

    @Override
    public Object find(String id) {
        return redisTemplate.opsForValue().get(id);
    }
}
