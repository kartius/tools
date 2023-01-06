package com.kartius.branching;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.*;


@Configuration
public class ApplicationProperties {
    @Value("${spring.profiles.active:}")
    private String activeProfile;
    @Value("#{${input.topics}}")
    private List<String> inputTopics;
    @Value("#{${output.topics}}")
    private Map<String, String> outputTopics;
    @Value("${affiliate.topic.name.pattern}")
    private String affiliateTopicNamePattern;
    @Value("${priority.topic.name.pattern}")
    private String priorityTopicNamePattern;
    @Value("${rule}")
    private String splittingRule;
    @Value("${kafka.applicationId}")
    private String applicationId;
    @Value("${kafka.bootstrapAddress}")
    private String kafkaAddresses;
    @Value("${kafka.properties.security.protocol}")
    private String protocol;
    @Value("${kafka.properties.sasl.mechanism}")
    private String mechanism;
    @Value("${kafka.properties.sasl.config}")
    private String apiKey;
    @Value("${kafka.properties.group.id}")
    private String groupId;

    private final UUID uuid = UUID.randomUUID();
    private final String groupUUID = uuid.toString();

    public List<String> getInputTopics() {
        return new ArrayList<String>(this.inputTopics);
    }

    public Map<String, String> getOutputTopics() {
        return this.outputTopics;
    }

    public String getAffiliateTopicNamePattern() {
        return this.affiliateTopicNamePattern;
    }

    public String getPriorityTopicNamePattern() {
        return priorityTopicNamePattern;
    }

    public String getSplittingRule() {
        return this.splittingRule;
    }

    public Properties getKafkaProperties() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, this.applicationId);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaAddresses);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, groupUUID);
        return props;
    }

}
