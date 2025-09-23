package com.project.enotes_api_service.Exception;

public class LinkExpiredException extends RuntimeException{
    public LinkExpiredException(String message) {
        super(message);
    }
}
