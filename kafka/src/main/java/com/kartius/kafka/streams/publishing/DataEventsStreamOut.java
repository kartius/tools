package com.kartius.kafka.streams.publishing;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface DataEventsStreamOut {

    String TOPIC_OUTPUT = "test-events-out";

    @Output(TOPIC_OUTPUT)
    MessageChannel outboundDataEvents();
}
