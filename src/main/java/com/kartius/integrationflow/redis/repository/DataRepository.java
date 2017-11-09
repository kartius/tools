package com.kartius.integrationflow.redis.repository;

import com.kartius.integrationflow.redis.model.CustomData;

public interface DataRepository {

    public void save(CustomData o);

    public CustomData find(String id);
}
