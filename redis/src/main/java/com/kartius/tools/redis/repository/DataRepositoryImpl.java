package com.kartius.tools.redis.repository;

import com.kartius.tools.redis.model.CustomData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DataRepositoryImpl implements DataRepository {
    @Autowired
    private RedisTemplate<String, CustomData> redisTemplateSession;

    @Override
    public void save(CustomData data) {
        redisTemplateSession.opsForValue().set( data.getId(), data);
    }
    @Override
    public CustomData find(String id) {
        return redisTemplateSession.opsForValue().get(id);
    }
}
