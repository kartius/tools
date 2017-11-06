package com.kartius.integrationflow.redis.repository;

public interface DataRepository {

    public void save(Object o);

    public Object find(String id);
}
