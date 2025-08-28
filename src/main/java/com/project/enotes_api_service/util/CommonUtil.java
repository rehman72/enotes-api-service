package com.project.enotes_api_service.util;

import com.project.enotes_api_service.Handler.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CommonUtil {

    public  static ResponseEntity<?> createBuildResponse(Object data, HttpStatus status) {
        GenericResponse response = GenericResponse.builder()
                .data(data)
                .responseStatus(status)
                .message("Success")
                .build();
        return response.create();
    }

    public  static ResponseEntity<?> createBuildResponseMessage(String message,HttpStatus status){
        GenericResponse response = GenericResponse.builder()
                .responseStatus(status)
                .status("Success")
                .message(message)
                .build();
        return response.create();
    }

    public  static ResponseEntity<?> createErrorResponse(Object data, HttpStatus httpStatus) {
        GenericResponse response = GenericResponse.builder()
                .responseStatus(httpStatus)
                .message("Failed")
                .data(data)
                .build();
        return response.create();
    }

    public  static ResponseEntity<?> createErrorResponseMessage(String message, HttpStatus httpStatus) {
        GenericResponse response = GenericResponse.builder()
                .responseStatus(httpStatus)
                .message("Failed")
                .message(message)
                .build();
        return response.create();
    }
}
