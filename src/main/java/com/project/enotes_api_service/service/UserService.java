package com.project.enotes_api_service.service;


import com.project.enotes_api_service.dto.PasswordChangeRequest;
import com.project.enotes_api_service.dto.PswdResetRequest;

public interface UserService {

    Boolean changePassword(PasswordChangeRequest changeRequest);

    Boolean sendEmailPasswordReset(String email,String url) throws Exception;

    Boolean verifyPasswordLink(Integer uid, String passResetToken)throws Exception;

    void resetPassword(PswdResetRequest passwordResetRequest) throws Exception;
}
