package com.project.enotes_api_service.Exception;

import java.util.Map;

public class ValidationException extends RuntimeException{

    private Map<String,Object> error;

    public ValidationException(Map<String,Object> error) {
        super("Validation Failed!");
        this.error = error;
    }

    public ValidationException(String msg) {
        super(msg);
    }

    public Map<String, Object> getError() {
        return error;
    }
}
