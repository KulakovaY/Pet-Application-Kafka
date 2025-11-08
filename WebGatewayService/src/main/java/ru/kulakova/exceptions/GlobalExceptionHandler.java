package ru.kulakova.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TimeoutException.class)
    public ResponseEntity<String> handleTimeout(TimeoutException ex) {
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
                .body("Kafka: timeout waiting for reply");
    }

    @ExceptionHandler(ExecutionException.class)
    public ResponseEntity<String> handleExecution(ExecutionException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body("Kafka execution error: " + ex.getCause().getMessage());
    }

    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity<String> handleInterrupted(InterruptedException ex) {
        Thread.currentThread().interrupt();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Kafka request interrupted");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unexpected error: " + ex.getMessage());
    }
}
