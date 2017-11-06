package com.kartius.integrationflow.redis.service;

public interface SessionCashing {

    public void save(Object o);

    public Object find(String id);
}
