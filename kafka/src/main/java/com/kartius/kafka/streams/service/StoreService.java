package com.kartius.kafka.streams.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kartius.kafka.data.DataEvent;
import com.kartius.kafka.streams.QueryableStoreFetcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@Slf4j
public class StoreService {

    @Autowired
    private StreamsBuilderFactoryBean kafkaStreamsBean;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private QueryableStoreFetcher queryableStoreFetcher;

    public List<DataEvent> getAllEvents() {
        ReadOnlyKeyValueStore dataStore = queryableStoreFetcher.getQueryableStore("dataStore-1", QueryableStoreTypes.keyValueStore(), kafkaStreamsBean.getKafkaStreams());
        List<DataEvent> events = new ArrayList<>();
        KeyValueIterator all = dataStore.all();
        all.forEachRemaining(c -> {
            String result = null;
            Object event = null;
            Object key = null;
            try {
                key = all.peekNextKey();
                if (key != null) {
                    event = dataStore.get(key);
                }
                if (event != null) {
                    result = event.toString();
                    DataEvent dataEvent = null;
                    try {
                        dataEvent = objectMapper.readValue(result.getBytes(), DataEvent.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    events.add(dataEvent);
                }
            } catch (Exception e) {
                System.out.println("Cant find anymore events");
            }
        });
        return events;
    }

    public long getCountEvents() {
        ReadOnlyKeyValueStore dataStore = queryableStoreFetcher.getQueryableStore("dataStore-1", QueryableStoreTypes.keyValueStore(), kafkaStreamsBean.getKafkaStreams());
        return dataStore.approximateNumEntries();
    }


    public DataEvent getEvent(String id) {
        ReadOnlyKeyValueStore dataStore = queryableStoreFetcher.getQueryableStore("dataStore", QueryableStoreTypes.keyValueStore(), kafkaStreamsBean.getKafkaStreams());
        String result = dataStore.get(id).toString();
        DataEvent dataEvent = null;
        try {
            dataEvent = objectMapper.readValue(result.getBytes(), DataEvent.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataEvent;
    }
}
