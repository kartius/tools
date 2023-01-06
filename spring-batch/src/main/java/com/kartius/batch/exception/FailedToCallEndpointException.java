package com.kartius.batch.exception;

public class FailedToCallEndpointException extends Exception {

    public FailedToCallEndpointException(String message, Throwable cause) {
        super(message, cause);
    }

}
