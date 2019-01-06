package com.kartius.tools.redis.controller;


import com.kartius.tools.redis.model.CustomData;
import com.kartius.tools.redis.service.ResponseCashing;
import com.kartius.tools.redis.service.SessionCashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/create/redisSession", method = RequestMethod.POST)
    public void createRedisSession(@RequestBody CustomData data) {
        sessionCashing.save(data);
    }


}
