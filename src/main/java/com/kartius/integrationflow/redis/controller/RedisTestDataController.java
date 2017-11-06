package com.kartius.integrationflow.redis.controller;

import com.kartius.integrationflow.redis.service.ResponseCashing;
import com.kartius.integrationflow.redis.service.SessionCashing;
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
@RequestMapping("/rest")
public class RedisTestDataController {

    @Autowired
    ResponseCashing responseCashing;

    @Autowired
    SessionCashing sessionCashing;

    @RequestMapping(value = "/get/redisResponse", method = RequestMethod.GET)
    public ResponseEntity getRedisResponse(@RequestParam String test) {
        return new ResponseEntity(responseCashing.getRedisData(test), HttpStatus.OK);
    }

    @RequestMapping(value = "/get/redisSession", method = RequestMethod.GET)
    public ResponseEntity getRedisSession(@RequestParam String session) {
        return new ResponseEntity(sessionCashing.find(session), HttpStatus.OK);
    }

    @RequestMapping(value = "/create/redisSession", method = RequestMethod.GET)
    public void createRedisSession(@RequestParam Object o) {
        sessionCashing.save(o);
    }


}
