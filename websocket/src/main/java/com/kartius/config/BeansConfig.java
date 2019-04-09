package com.kartius.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.kartius.websocket.WebSocketClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class BeansConfig {



    @Bean
    public WebSocketClient webSocketClient(){
        return new WebSocketClient();
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return Jackson2ObjectMapperBuilder.json().modules(new JavaTimeModule(), new Jdk8Module(), new ParameterNamesModule()).
                featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).build();
    }

}
