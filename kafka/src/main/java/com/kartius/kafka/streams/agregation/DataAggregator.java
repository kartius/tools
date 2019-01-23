package com.kartius.kafka.streams.agregation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kartius.kafka.data.DataEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.StreamsBuilderFactoryBean;
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
//        countAgregationByTime(kStreamData);
        kafkaStreamsBean.start();

    }

    private void simpleAggregationByKey(KStream<String, String> kStreamData) throws Exception {
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

//    private void countAgregationByTime(KStream<String, String> kStreamData) throws Exception {
//        KTable<Windowed<String>, Long> kTableResult = kStreamData.selectKey((k, v) -> {
//            DataEvent event;
//            String id = null;
//            try {
//                event = objectMapper.readValue(v, DataEvent.class);
//                id = event.getId();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return id;
//        }).groupByKey()
//                .windowedBy(TimeWindows.of(50000))
//                .count(Materialized.as("dataStore-1"));
//        kStreamData.print();
//    }

}
