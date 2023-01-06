package com.kartius.branching;


import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class StreamsBranchHandler {

    @Autowired
    private ApplicationProperties appConfiguration;

    private static final Logger LOGGER = LoggerFactory.getLogger(StreamsBranchHandler.class);

    @PostConstruct
    public void startStreaming() {
        dispatch();
    }

    private void dispatch() {
        final Topology topology = buildTopology();
        KafkaStreams kafkaStreams = new KafkaStreams(topology, appConfiguration.getKafkaProperties());
        kafkaStreams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
    }

    Topology buildTopology() {
        List<String> inputTopics = appConfiguration.getInputTopics();
        Map<String, String> outputTopics = appConfiguration.getOutputTopics();
        List<String> outputTopicIds = new ArrayList<>(outputTopics.keySet());

        final boolean firstRule = appConfiguration.getSplittingRule().equals("First Rule");
        final boolean secondRule = appConfiguration.getSplittingRule().equals("Second Rule");

        StreamsBuilder builder = new StreamsBuilder();

        List<Predicate<String, String>> predicates = new ArrayList<>();

        for (String id : outputTopicIds) {
            if (firstRule) {
                predicates.add((key, value) -> conditionForFirstRule(id, value));
            } else if (secondRule) {
                predicates.add((key, value) -> conditionForSecondRule(id, value));
            } else {
                LOGGER.error("No splitting rule found.");
            }
        }

        Predicate<String, String>[] arrayPredicates = new Predicate[predicates.size()];
        arrayPredicates = predicates.toArray(arrayPredicates);
        KStream<String, String>[] branches = builder.<String, String>stream(inputTopics).branch(arrayPredicates);

        for (int i = 0; i < outputTopicIds.size(); i++) {
            branches[i].to(outputTopicIds.get(i));
        }
        return builder.build();
    }

    private boolean conditionForFirstRule(String id, String json) {
        return true;
    }

    private boolean conditionForSecondRule(String id, String json) {
        return true;
    }

}
