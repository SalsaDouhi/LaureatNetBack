package com.eheiste.laureatnet.exception.exceptions;

public class InvalidJwtException extends RuntimeException {
    public InvalidJwtException(String message, Throwable cause) {
        super(message, cause);
    }
}
