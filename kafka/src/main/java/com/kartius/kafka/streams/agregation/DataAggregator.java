package com.kartius.kafka.streams.agregation;

import com.kartius.kafka.data.DataEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
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
    private KStream<String, String> kStreamData;
    private KTable<String, String> kTableData;

    @PostConstruct
    public void startStreaming() throws Exception {
        kStreamData = kafkaStreamsBean.getObject().stream("data-events");
        kTableData = kStreamData.selectKey((k, v) -> {
            DataEvent event;
            String result;
            result = v;
            log.info("-------------- " + result);
            return result;
        }).groupByKey().reduce((aggValue, newValue) -> aggValue, Materialized.as("dataStore"));
        kafkaStreamsBean.start();

    }

}
