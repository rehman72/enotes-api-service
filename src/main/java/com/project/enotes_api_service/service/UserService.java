package com.project.enotes_api_service.service;

import com.project.enotes_api_service.dto.LoginRequest;
import com.project.enotes_api_service.dto.LoginResponse;
import com.project.enotes_api_service.dto.UserDto;

public interface UserService {

    Boolean register(UserDto userDto, String  httpServletRequest) throws Exception;

    LoginResponse login(LoginRequest loginRequest);
}
