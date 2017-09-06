package com.kartius.integrationflow.redis.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Component
public class ServiceKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder key = new StringBuilder();
        //include method name in key
        key.append(method.getName());
        for (Object argument : params) {
            if (argument != null) {
                key.append('-');
                key.append(argument);
            }
        }
        return key.toString();
    }
}
