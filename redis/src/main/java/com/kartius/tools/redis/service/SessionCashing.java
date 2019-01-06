package com.kartius.tools.redis.service;


import com.kartius.tools.redis.model.CustomData;

public interface SessionCashing {

    void save(CustomData data);

    CustomData find(String id);
}
