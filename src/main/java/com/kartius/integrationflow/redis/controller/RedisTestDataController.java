package com.kartius.integrationflow.redis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/rest/get")
@EnableCaching
public class RedisTestDataController {
    private static Logger LOG = LoggerFactory.getLogger(RedisTestDataController.class);

    @Cacheable(keyGenerator = "serviceKeyGenerator")
    @RequestMapping(value = "/redisData", method = RequestMethod.GET)
    public ResponseEntity getRedisData(@RequestParam String test) {
        List<String> result = Arrays.asList("Test1", "Test2", "Test3");
        LOG.info("call getRedisData method with attribute - " + test);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
