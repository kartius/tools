package com.kartius.kafka.config;

import com.kartius.kafka.streams.publishing.DataEventsStreamOut;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(DataEventsStreamOut.class)
public class StreamsConfig {

}
