package com.project.enotes_api_service.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@Slf4j
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler{

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        log.info("Access Denied Handler :: {}",accessDeniedException.getMessage());
        handlerExceptionResolver.resolveException(request,response,null,accessDeniedException);
    }

}
