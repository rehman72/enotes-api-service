package com.project.enotes_api_service.Exception;

import com.project.enotes_api_service.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.io.FileNotFoundException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e){
        log.error("GobalExceptionHandler:: handleException ::",e.getMessage());
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleException(IllegalArgumentException e){
        return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> nullPointerExceptionHandler(Exception e){
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> validationExceptionHandler(ValidationException exception){
//        return new ResponseEntity<>(exception.getError(), HttpStatus.BAD_REQUEST);
        return CommonUtil.createErrorResponse(exception.getError(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        return CommonUtil.createErrorResponseMessage(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<?> handleAlreadyExistException(AlreadyExistException ex) {
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        return CommonUtil.createErrorResponseMessage(ex.getMessage(),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ExtensionNotAllowedException.class)
    public ResponseEntity<?> handleHttpClientErrorException(ExtensionNotAllowedException ex) {
        return CommonUtil.createErrorResponseMessage(ex.getMessage(),HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException ex) {
        return CommonUtil.createErrorResponseMessage(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

}
