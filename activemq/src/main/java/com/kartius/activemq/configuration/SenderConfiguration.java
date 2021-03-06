//package com.kartius.activemq.configuration;
//
//import com.kartius.activemq.producer.Sender;
//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jms.connection.CachingConnectionFactory;
//import org.springframework.jms.core.JmsTemplate;
//
//@Configuration
//public class SenderConfiguration {
//
////    @Value("${activemq.broker-url}")
////    private String brokerUrl;
//
//    @Bean
//    public ActiveMQConnectionFactory activeMQConnectionFactory() {
//        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
//        activeMQConnectionFactory.setBrokerURL("tcp://localhost:61616");
////        activeMQConnectionFactory.setTrustAllPackages(true);
//        return activeMQConnectionFactory;
//    }
//
//    @Bean
//    public CachingConnectionFactory cachingConnectionFactory() {
//        return new CachingConnectionFactory(activeMQConnectionFactory());
//    }
//
//    @Bean
//    public JmsTemplate jmsTemplate() {
//        return new JmsTemplate(cachingConnectionFactory());
//    }
//
//    @Bean
//    public Sender sender() {
//        return new Sender();
//    }
//}
