package com.kartius.kafka.streams.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kartius.kafka.data.DataEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class StoreService {

    @Autowired
    private StreamsBuilderFactoryBean kafkaStreamsBean;

    @Autowired
    private ObjectMapper objectMapper;

//    public Long getCountEvents(String id) {
//        final ReadOnlyKeyValueStore<String, Long> store = kafkaStreamsBean.getKafkaStreams().store("dataStore-1",
//                QueryableStoreTypes.keyValueStore());
//        return store.get(id);
//    }

    public DataEvent getEvent(String id) {
        final ReadOnlyKeyValueStore<String, String> store = kafkaStreamsBean.getKafkaStreams().store("dataStore",
                QueryableStoreTypes.keyValueStore());
        String result = store.get(id);
        DataEvent dataEvent = null;
        try {
            dataEvent = objectMapper.readValue(result.getBytes(), DataEvent.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataEvent;
    }
}
