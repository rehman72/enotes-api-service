package com.project.enotes_api_service.Security;

import com.project.enotes_api_service.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    private  HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    public JwtFilter(HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
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
