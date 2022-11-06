package com.misterstorm.ead.authuser.controllers.handleError;

import com.misterstorm.ead.authuser.service.exception.EmailAlreadyExistsException;
import com.misterstorm.ead.authuser.service.exception.UserNotFoundException;
import com.misterstorm.ead.authuser.service.exception.UsernameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleUserNotFoundException(UserNotFoundException e, WebRequest request) {
        return new ErrorMessage(e.getMessage(), HttpStatus.NOT_FOUND.toString(), e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e, WebRequest request) {
        return new ErrorMessage(e.getMessage(), HttpStatus.CONFLICT.toString(), e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handleEmailAlreadyExistsException(EmailAlreadyExistsException e, WebRequest request) {
        return new ErrorMessage(e.getMessage(), HttpStatus.CONFLICT.toString(), e.getMessage(), LocalDateTime.now());
    }

    public class ErrorMessage {
        private String message;
        private String statusCode;
        private String Description;
        private LocalDateTime timestamp;

        public ErrorMessage(String message, String statusCode, String description, LocalDateTime timestamp) {
            this.message = message;
            this.statusCode = statusCode;
            Description = description;
            this.timestamp = timestamp;
        }

        public String getMessage() {
            return message;
        }

        public String getStatusCode() {
            return statusCode;
        }

        public String getDescription() {
            return Description;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }
    }
    }
