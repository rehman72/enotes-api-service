package com.project.enotes_api_service.Security;

import com.project.enotes_api_service.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    private final HandlerExceptionResolver handlerExceptionResolver;


    public JwtFilter(HandlerExceptionResolver handlerExceptionResolver, UserDetailsService userDetailsService, JwtService jwtService) {
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
        String authorization = request.getHeader("Authorization");
        String token = "";
        String username = "";
        if (authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization;
            token = authorization.substring(7);
            username += jwtService.extractUsername(token);

            if (!username.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                Boolean validateToken = jwtService.validateToken(token, userDetails);
                if (validateToken) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                            userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(userDetails);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    log.info("Security Context Holder {}", SecurityContextHolder.getContext().getAuthentication().toString());
                }

            }
        }
            filterChain.doFilter(request, response);
        }catch (Exception e) {
            handlerExceptionResolver.resolveException(request,response,null,e);
        }



    }

}
