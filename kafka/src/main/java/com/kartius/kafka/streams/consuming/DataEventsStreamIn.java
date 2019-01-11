package com.kartius.kafka.streams.consuming;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface DataEventsStreamIn {

    String TOPIC_INPUT = "test-events-in";

    @Input(TOPIC_INPUT)
    SubscribableChannel input();
}
