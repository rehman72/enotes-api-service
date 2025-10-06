package com.project.enotes_api_service.Endpoint;

import com.project.enotes_api_service.dto.LoginRequest;
import com.project.enotes_api_service.dto.UserRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/auth")
public interface AuthEndPoint {

    @PostMapping("/register")
    ResponseEntity<?> registerUser(@RequestBody UserRequest userDto, HttpServletRequest serverRequest) throws Exception;

    @PostMapping("/login")
    ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest);
}
