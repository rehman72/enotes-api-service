package com.project.enotes_api_service.Exception;

import com.project.enotes_api_service.util.CommonUtil;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error("GlobalExceptionHandler:: handleException ::{}", e.getMessage());
             log.error("Unexpected error occurred: ", e);
        return CommonUtil.createErrorResponseMessage(
                "An unexpected error occurred. Please try again later.",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
        @ExceptionHandler({AuthenticationException.class})
        public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex) {
            log.warn("Authentication failure: ",ex);
            return CommonUtil.createErrorResponseMessage(
                    "Authentication failed. Invalid credentials.",
                    HttpStatus.UNAUTHORIZED
            );
        }

        @ExceptionHandler({JwtException.class})
        public ResponseEntity<?> handleJwtException(JwtException ex) {
            log.warn("Authentication failure: ",ex);
            return CommonUtil.createErrorResponseMessage(
                    "Authentication failed. Invalid credentials.",
                    HttpStatus.UNAUTHORIZED
            );
        }


        @ExceptionHandler({AccessDeniedException.class})
        public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex) {
            log.warn("Access denied: ", ex);
            return CommonUtil.createErrorResponseMessage(
                    "You are not authorized to perform this action.",
                    HttpStatus.FORBIDDEN
            );
        }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIOExceptions(IOException e){
        log.error("GlobalExceptionHandler:: handleIOExceptions :: {}",e.getMessage());
        if(e instanceof FileAlreadyExistsException){
            return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.CONFLICT);
        }
    return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SuccessException.class)
    public ResponseEntity<?> handleSuccessException(SuccessException e){
        log.error("GlobalExceptionHandler:: handleSuccessException :: {}",e.getMessage());
        return CommonUtil.createBuildResponseMessage(e.getMessage(),HttpStatus.OK);
    }
    @ExceptionHandler(JwtTokenExpiredException.class)
    public ResponseEntity<?> handleJwtTokenExpiredException(JwtTokenExpiredException e){
        log.error("GlobalExceptionHandler:: handleJwtTokenExpiredException :: {}",e.getMessage());
        return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AccountNotVerifiedException.class)
    public ResponseEntity<?> accountNotVerifiedException(AccountNotVerifiedException e){
        log.error("GlobalExceptionHandler:: handleAccountNotVerifiedException :: {}",e.getMessage());
        return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> nullPointerExceptionHandler(Exception e){
        log.error("GlobalExceptionHandler:: handleNullPointerExceptionHandler :: {}",e.getMessage());
        return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> validationExceptionHandler(ValidationException exception){
        log.error("GlobalExceptionHandler:: handleValidationExceptionHandler :: {}",exception.getMessage());
        return CommonUtil.createErrorResponse(exception.getError(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("GlobalExceptionHandler:: handleResourceNotFoundException :: {}",ex.getMessage());
        return CommonUtil.createErrorResponseMessage(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<?> handleAlreadyExistException(AlreadyExistException ex) {
        log.error("GlobalExceptionHandler:: handleAlreadyExistException :: {}",ex.getMessage());
        return CommonUtil.createErrorResponseMessage(ex.getMessage(),HttpStatus.CONFLICT);
    }
    @ExceptionHandler(LinkExpiredException.class)
    public ResponseEntity<?> handleLinkExpiredException(LinkExpiredException ex) {
        log.error("GlobalExceptionHandler:: handleLinkExpiredException :: {}",ex.getMessage());
        return CommonUtil.createErrorResponseMessage(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExtensionNotAllowedException.class)
    public ResponseEntity<?> handleExtensionNotAllowedException(ExtensionNotAllowedException ex) {
        log.error("GlobalExceptionHandler:: handleExtensionNotAllowedException :: {}",ex.getMessage());
        return CommonUtil.createErrorResponseMessage(ex.getMessage(),HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException ex) {
        log.error("GlobalExceptionHandler:: handleFileNotFoundException :: {}",ex.getMessage());
        return CommonUtil.createErrorResponseMessage(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PasswordNotMatchedException.class)
    public ResponseEntity<?> handlePasswordChangeException(PasswordNotMatchedException ex) {
        log.error("GlobalExceptionHandler:: handlePasswordChangeException :: {}",ex.getMessage());
        return CommonUtil.createErrorResponseMessage(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RegisterationException.class)
    public ResponseEntity<?> handleRegistrationException(RegisterationException e){
        Throwable cause = e.getCause();
        HttpStatus httpStatus=HttpStatus.INTERNAL_SERVER_ERROR;

        if(cause instanceof ValidationException) httpStatus=HttpStatus.BAD_REQUEST;

        log.error("Registration Failed!",e);

        return CommonUtil.createErrorResponseMessage
                ("Registration failed! Please Check Your Input!",httpStatus);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadable(HttpMessageNotReadableException e){
        return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.NOT_FOUND);
    }


}
