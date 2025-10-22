package com.project.enotes_api_service.Security;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.security.web.access.AccessDeniedHandler;
import java.io.IOException;

@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler{

    private final HandlerExceptionResolver handlerExceptionResolver;

    @PostConstruct
    public void logHandlerResolverType() {
        log.info("CustomAccessDeniedHandler HandlerExceptionResolver: {}" ,handlerExceptionResolver.getClass().getName());
    }

    public CustomAccessDeniedHandler(HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        log.info("Access Denied Handler :: {}",accessDeniedException.getMessage());
        handlerExceptionResolver.resolveException(request,response,null,accessDeniedException);
    }

}
