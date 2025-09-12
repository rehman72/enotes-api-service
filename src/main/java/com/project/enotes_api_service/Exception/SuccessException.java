package com.project.enotes_api_service.Exception;

import lombok.experimental.StandardException;

@StandardException
public class SuccessException extends RuntimeException {

    public SuccessException(String message) {
        super(message);
    }
}
