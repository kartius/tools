package com.kartius.kafka.streams.consuming;

import com.kartius.kafka.data.DataEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;

@EnableBinding(DataEventsStreamIn.class)
@Slf4j
public class DataConsumer {

    @StreamListener(DataEventsStreamIn.TOPIC_INPUT)
    public void consume(@Payload DataEvent event){
        log.info(event.toString());
    }
}
