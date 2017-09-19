package com.kartius.integrationflow;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class ConfigApplication {

    //redis
    @Value("${redis.url}")
    private String redisUrl;
    @Value("${redis.port}")
    private String redisPort;
    @Value("${redis.expiration}")
    private String redisExpiration;

    public String getRedisUrl() {
        return redisUrl;
    }

    public void setRedisUrl(String redisUrl) {
        this.redisUrl = redisUrl;
    }

    public String getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(String redisPort) {
        this.redisPort = redisPort;
    }

    public String getRedisExpiration() {
        return redisExpiration;
    }

    public void setRedisExpiration(String redisExpiration) {
        this.redisExpiration = redisExpiration;
    }

}
