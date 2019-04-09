package com.kartius.data;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class RequestEvent {
   String type;

    @JsonCreator
    public RequestEvent(@JsonProperty("type") String type) {
        this.type = type;
    }
}
