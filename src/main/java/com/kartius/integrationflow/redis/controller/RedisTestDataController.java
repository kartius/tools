package com.kartius.integrationflow.redis.controller;

import com.kartius.integrationflow.redis.service.ResponseCashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/rest/get")
public class RedisTestDataController {

    @Autowired
    ResponseCashing responseCashing;

    @RequestMapping(value = "/redisData", method = RequestMethod.GET)
    public ResponseEntity getRedisData(@RequestParam String test) {
        return new ResponseEntity(responseCashing.getRedisData(test), HttpStatus.OK);
    }
}
