package com.kartius.integrationflow.redis.service.impl;

import com.kartius.integrationflow.redis.repository.DataRepository;
import com.kartius.integrationflow.redis.service.SessionCashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class SessionCashingImpl implements SessionCashing {

    @Autowired
    private DataRepository dataRepository;

    public void save(Object o) {
        dataRepository.save(o);
    }

    public Object find(String id) {
        return dataRepository.find(id);
    }
}
