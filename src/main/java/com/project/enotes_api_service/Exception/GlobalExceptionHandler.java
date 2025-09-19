package com.project.enotes_api_service.Exception;

import com.project.enotes_api_service.util.CommonUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception e){
        log.error("GobalExceptionHandler:: handleException ::",e.getMessage());
        ProblemDetail errorDetails=null;
        if(e instanceof AuthenticationException){
             errorDetails=ProblemDetail.
                    forStatusAndDetail(HttpStatus.valueOf(401),e.getMessage());
            errorDetails.setProperty("access_denied_reason","Authentication Failure");
            return errorDetails;
        }
        if(e instanceof AccessDeniedException){
             errorDetails=ProblemDetail
                    .forStatusAndDetail(HttpStatus.valueOf(403),e.getMessage());
            errorDetails.setProperty("access_denied_reason","Not_Authorized");
        }

        if(e instanceof SecurityException){
            errorDetails=ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(403),e.getMessage());
            errorDetails.setProperty("access_denied_reason","Invalid Signature");
        }
        if(e instanceof ExpiredJwtException){
            errorDetails=ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(403),e.getMessage());
            errorDetails.setProperty("access_denied_reason","Token Expired!");
        }
        return errorDetails;
    }


    @ExceptionHandler(SuccessException.class)
    public ResponseEntity<?> handleSuccessException(SuccessException e){
        return CommonUtil.createBuildResponseMessage(e.getMessage(),HttpStatus.OK);
    }
    @ExceptionHandler(JwtTokenExpiredException.class)
    public ResponseEntity<?> handleJwtTokenExpiredException(JwtTokenExpiredException e){
        return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AccountNotVerifiedException.class)
    public ResponseEntity<?> handleSuccessException(AccountNotVerifiedException e){
        return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> nullPointerExceptionHandler(Exception e){
        return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> validationExceptionHandler(ValidationException exception){
        return CommonUtil.createErrorResponse(exception.getError(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return CommonUtil.createErrorResponseMessage(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<?> handleAlreadyExistException(AlreadyExistException ex) {
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

    @ExceptionHandler(MailSendException.class)
//    @ResponseBody
//    @ResponseStatus
    public ResponseEntity<?> handleMailSendException(MailSendException e){
        Exception[] error=e.getMessageExceptions();
        String message = Arrays.stream(error)
                .findFirst()
                .get()
                .getMessage();
        log.info("MailSendException:: handleMailSendException :: {}",message);
        return CommonUtil.createErrorResponseMessage(message,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
