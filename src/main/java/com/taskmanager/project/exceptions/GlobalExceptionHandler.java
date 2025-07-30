package com.taskmanager.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<?> handleEmailAlreadyInUse(EmailAlreadyInUseException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).
                body(new ErrorResponse("error", e.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(new ErrorResponse("error", e.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentials(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                body(new ErrorResponse("error", e.getMessage()));
    }

    static class ErrorResponse {
        private String type;
        private String message;

        public ErrorResponse(String type, String message) {
            this.type = type;
            this.message = message;
        }
    }
}
