package com.kartius.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/rest/get")
@EnableCaching
public class ComponentController {
    private static Logger LOG = LoggerFactory.getLogger(ComponentController.class);

    @Cacheable(keyGenerator = "serviceKeyGenerator",cacheNames = "hybXML")
    @RequestMapping(value = "/redisData", method = RequestMethod.GET)
    public ResponseEntity getSubCategories(@RequestParam String test) {
        List<String> result = Arrays.asList("Test1", "Test2", "Test3");
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/redisData1", method = RequestMethod.GET)
    @Cacheable(keyGenerator = "serviceKeyGenerator",cacheNames = "hybXML")
    public List<String> getTest(@RequestParam String test) {
        List<String> result = Arrays.asList("Test1", "Test2", "Test3");
        return result;
    }

}
