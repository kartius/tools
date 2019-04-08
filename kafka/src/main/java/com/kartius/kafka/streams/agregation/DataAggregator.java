package com.kartius.kafka.streams.agregation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kartius.kafka.data.DataEvent;
import com.kartius.kafka.streams.QueryableStoreFetcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.StreamsBuilderFactoryBean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.io.IOException;

@Slf4j
@Component
public class DataAggregator {

    @Autowired
    private StreamsBuilderFactoryBean kafkaStreamsBean;
    @Autowired
    private ObjectMapper objectMapper;
    private KStream<String, String> kStreamData;
    private KTable<String, String> kTableData;

    @PostConstruct
    public void startStreaming() throws Exception {
        kStreamData = kafkaStreamsBean.getObject().stream("data-events");

        simpleAggregationByKey(kStreamData);
        countAgregationByTime(kStreamData);
        kafkaStreamsBean.start();

    }

    public void simpleAggregationByKey(KStream<String, String> kStreamData) {
        kTableData = kStreamData.selectKey((k, v) -> {
            DataEvent event;
            String id = null;
            try {
                event = objectMapper.readValue(v, DataEvent.class);
                id = event.getId();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return id;
        }).groupByKey().
                reduce((aggValue, newValue) -> aggValue, Materialized.as("dataStore"));
    }

    public void countAgregationByTime(KStream<String, String> kStreamData) {
        kTableData = kStreamData.selectKey((k, v) -> {
            DataEvent event;
            String id = null;
            try {
                event = objectMapper.readValue(v, DataEvent.class);
                id = event.getId();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return id;
        }).groupByKey().
                reduce((aggValue, newValue) -> aggValue, Materialized.as("dataStore-1"));
    }

}
