package com.kartius.tools.redis.service.impl;

import com.kartius.tools.redis.model.CustomData;
import com.kartius.tools.redis.repository.DataRepository;
import com.kartius.tools.redis.service.SessionCashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionCashingImpl implements SessionCashing {

    @Autowired
    private DataRepository dataRepository;

    public void save(CustomData data) {
        dataRepository.save(data);
    }

    public CustomData find(String id) {
        return dataRepository.find(id);
    }
}
