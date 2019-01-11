package com.kartius.kafka.streams.publishing;

import com.kartius.kafka.data.DataEvent;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

@Component
public class DataPublisher {

    private final DataEventsStreamOut dataEventsStreamOut;

    public DataPublisher(DataEventsStreamOut dataEventsStreamOut) {
        this.dataEventsStreamOut = dataEventsStreamOut;
    }

    public void publish(DataEvent event) {
        MessageChannel messageChannel = dataEventsStreamOut.outboundDataEvents();
        messageChannel.send(MessageBuilder.withPayload(event).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
    }
}
