package com.kartius.tools.redis.repository;


import com.kartius.tools.redis.model.CustomData;

public interface DataRepository {

    void save(CustomData o);

    CustomData find(String id);
}
