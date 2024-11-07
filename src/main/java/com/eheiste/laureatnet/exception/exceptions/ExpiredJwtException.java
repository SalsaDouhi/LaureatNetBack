package com.eheiste.laureatnet.exception.exceptions;

public class ExpiredJwtException extends RuntimeException {
    public ExpiredJwtException(String message, Throwable cause) {
        super(message, cause);
    }
}
