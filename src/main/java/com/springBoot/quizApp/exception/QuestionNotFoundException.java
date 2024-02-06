package com.springBoot.quizApp.exception;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(String message) {
        super(message);
    }
    public QuestionNotFoundException(Throwable cause) {
        super(cause);
    }
    public QuestionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
