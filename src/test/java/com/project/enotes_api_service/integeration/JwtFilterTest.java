package com.project.enotes_api_service.integeration;

import com.project.enotes_api_service.Security.JwtFilter;
import com.project.enotes_api_service.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.HandlerExceptionResolver;


import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class JwtFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private FilterChain filterChain;

    @Mock
    private HandlerExceptionResolver handlerExceptionResolver;

    private JwtFilter jwtFilter;

    @Mock
    private UserDetails userDetails;

    private MockHttpServletRequest request;

    private MockHttpServletResponse response;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        jwtFilter = new JwtFilter(handlerExceptionResolver, userDetailsService, jwtService);
        SecurityContextHolder.clearContext();
    }

@Test
public void testDoFilterInternal_ValidToken_SetsAuthentication() throws ServletException, IOException {
            // Arrange
            String token = "testToken";
            String username = "testUser";

            request.addHeader("Authorization", "Bearer " + token);
            when(jwtService.extractUsername(token)).thenReturn(username);
            when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
            when(jwtService.validateToken(token, userDetails)).thenReturn(true);
            when(userDetails.getAuthorities()).thenReturn(List.of());

            // Act
            jwtFilter.doFilter(request, response, filterChain);
            // Assert
            verify(filterChain, times(1)).doFilter(request, response);
            verify(jwtService).extractUsername(token);
            verify(userDetailsService).loadUserByUsername(username);
            verify(jwtService).validateToken(token, userDetails);

            var auth = SecurityContextHolder.getContext().getAuthentication();
            assertNotNull(auth, "Authentication should not be null");
            assertInstanceOf(UsernamePasswordAuthenticationToken.class, auth);
            assertEquals(userDetails, auth.getPrincipal());
        }
}
