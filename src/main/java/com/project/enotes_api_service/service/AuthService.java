package com.project.enotes_api_service.service;

import com.project.enotes_api_service.Exception.RegisterationException;
import com.project.enotes_api_service.dto.LoginRequest;
import com.project.enotes_api_service.dto.LoginResponse;
import com.project.enotes_api_service.dto.UserRequest;

public interface AuthService {

    Boolean register(UserRequest userDto, String  httpServletRequest) throws  RegisterationException;

    LoginResponse login(LoginRequest loginRequest);
}

