package com.project.enotes_api_service.Exception;

public class PasswordNotMatchedException extends RuntimeException {
    public PasswordNotMatchedException(String message) {
        super(message);
    }
}
