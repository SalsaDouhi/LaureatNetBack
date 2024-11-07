package com.eheiste.laureatnet.exception;

import com.eheiste.laureatnet.exception.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = ApiRequestException.class)
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        ApiExceptionResponse apiException = ApiExceptionResponse.builder()
                .message(e.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .throwable(e)
                .zonedDateTime(ZonedDateTime.now())
                .build();

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFountException.class)
    public ResponseEntity<Object> handleApiRequestException(NotFountException e) {
        ApiExceptionResponse apiException = ApiExceptionResponse.builder()
                .message(e.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND)
                .throwable(e)
                .zonedDateTime(ZonedDateTime.now())
                .build();

        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity<Object> handleApiRequestException(UnauthorizedException e) {
        ApiExceptionResponse apiException = ApiExceptionResponse.builder()
                .message(e.getMessage())
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .throwable(e)
                .zonedDateTime(ZonedDateTime.now())
                .build();

        return new ResponseEntity<>(apiException, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<Object> handleApiRequestException(AccessDeniedException e) {
        ApiExceptionResponse apiException = ApiExceptionResponse.builder()
                .message(e.getMessage())
                .httpStatus(HttpStatus.FORBIDDEN)
                .throwable(e)
                .zonedDateTime(ZonedDateTime.now())
                .build();

        return new ResponseEntity<>(apiException, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = UnsupportedMediaTypeException.class)
    public ResponseEntity<Object> handleApiRequestException(UnsupportedMediaTypeException e) {
        ApiExceptionResponse apiException = ApiExceptionResponse.builder()
                .message(e.getMessage())
                .httpStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .throwable(e)
                .zonedDateTime(ZonedDateTime.now())
                .build();

        return new ResponseEntity<>(apiException, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(value = RequestTimeoutException.class)
    public ResponseEntity<Object> handleApiRequestException(RequestTimeoutException e) {
        ApiExceptionResponse apiException = ApiExceptionResponse.builder()
                .message(e.getMessage())
                .httpStatus(HttpStatus.REQUEST_TIMEOUT)
                .throwable(e)
                .zonedDateTime(ZonedDateTime.now())
                .build();

        return new ResponseEntity<>(apiException, HttpStatus.REQUEST_TIMEOUT);
    }


    @ExceptionHandler(value = InternalServerErrorException.class)
    public ResponseEntity<Object> handleApiRequestException(InternalServerErrorException e) {
        ApiExceptionResponse apiException = ApiExceptionResponse.builder()
                .message(e.getMessage())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .throwable(e)
                .zonedDateTime(ZonedDateTime.now())
                .build();

        return new ResponseEntity<>(apiException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = InvalidJwtException.class)
    public ResponseEntity<Object> handleInvalidJwtException(InvalidJwtException e) {
        ApiExceptionResponse apiException = ApiExceptionResponse.builder()
                .message(e.getMessage())
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .throwable(e)
                .zonedDateTime(ZonedDateTime.now())
                .build();

        return new ResponseEntity<>(apiException, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException e) {
        ApiExceptionResponse apiException = ApiExceptionResponse.builder()
                .message(e.getMessage())
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .throwable(e)
                .zonedDateTime(ZonedDateTime.now())
                .build();

        return new ResponseEntity<>(apiException, HttpStatus.UNAUTHORIZED);
    }

}
