package com.project.enotes_api_service.service;


import com.project.enotes_api_service.dto.PasswordChangeRequest;

public interface UserService {

    Boolean changePassword(PasswordChangeRequest changeRequest);
}
