package com.kartius.integrationflow.redis.service;

import com.kartius.integrationflow.redis.model.CustomData;

public interface SessionCashing {

    public void save(CustomData data);

    public CustomData find(String id);
}
