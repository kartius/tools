package com.kartius.tools.redis.service.impl;

import com.kartius.tools.redis.service.ResponseCashing;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@EnableCaching
@Service
public class ResponseCashingImpl implements ResponseCashing {

    @Override
    @Cacheable(cacheNames ="redisData" ,keyGenerator = "serviceKeyGenerator")
    public List<String> getRedisData(String test) {
        List<String> result = Arrays.asList("Test1", "Test2", "Test3");
        return result;
    }
}
