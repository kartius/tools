package com.kartius.integrationflow.redis.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ResponseCashing {

    public List<String> getRedisData(@RequestParam String test);
}
