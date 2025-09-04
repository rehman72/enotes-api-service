package com.project.enotes_api_service.util;

import com.project.enotes_api_service.Handler.GenericResponse;
import com.project.enotes_api_service.entity.FileDetails;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class CommonUtil {

    public  static ResponseEntity<?> createBuildResponse(Object data, HttpStatus status) {
        GenericResponse response = GenericResponse.builder()
                .responseStatus(status)
                .message("Success")
                .data(data)
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
                .message(message)
                .status("Failed")
                .responseStatus(httpStatus)
                .build();
        return response.create();
    }

    public static MediaType getContentType(FileDetails fileDetails) {
        String extension = FilenameUtils.getExtension(fileDetails.getOriginalFileName());

        return switch (extension) {
            case "pdf" -> MediaType.APPLICATION_PDF;
            case "jpg" -> MediaType.IMAGE_JPEG;
            case "png" -> MediaType.IMAGE_PNG;
            case "txt" -> MediaType.TEXT_PLAIN;
            default -> MediaType.APPLICATION_OCTET_STREAM;
        };
    }
}
