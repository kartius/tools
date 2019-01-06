package com.kartius.tools.redis.service;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ResponseCashing {

    public List<String> getRedisData(@RequestParam String test);
}
