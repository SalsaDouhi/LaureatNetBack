package com.eheiste.laureatnet.exception.exceptions;

public class RequestTimeoutException extends RuntimeException {
    public RequestTimeoutException(String message) {
        super(message);
    }
}