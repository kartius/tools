package com.kartius.activemq.consumer;

import com.kartius.activemq.data.CustomData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    private JmsTemplate jmsTemplate;

//    private CountDownLatch latch = new CountDownLatch(1);
//
//    public CountDownLatch getLatch() {
//        return latch;
//    }

     @JmsListener(destination = "tcp://localhost:61616")
    public Message receive(String destination) {
//        latch.countDown();
        String text = null;
        Message receive1 = jmsTemplate.receive(destination);
//        ActiveMQTextMessage receive = (ActiveMQTextMessage) jmsTemplate.receive(destination);
//        try {
//            text = receive.getText();
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
        return receive1;
    }

    public CustomData receiveObject(String destination) {
        ObjectMessage receive = (ObjectMessage) jmsTemplate.receive(destination);
        CustomData object = null;
        try {
            object = (CustomData) receive.getObject();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return object;
    }
}
