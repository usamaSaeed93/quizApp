package com.springBoot.quizApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice

public class RestExceptionHandler {
    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<QuestionErrorResponse> handleQuizAppException(QuestionNotFoundException exc) {
        // Create a student error response
        QuestionErrorResponse errorResponse = new QuestionErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage((exc.getMessage()));
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
