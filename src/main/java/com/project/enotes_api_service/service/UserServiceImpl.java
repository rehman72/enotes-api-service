package com.project.enotes_api_service.service;

import com.project.enotes_api_service.Exception.PasswordNotMatchedException;
import com.project.enotes_api_service.dto.PasswordChangeRequest;
import com.project.enotes_api_service.entity.User;
import com.project.enotes_api_service.repository.UserRepository;
import com.project.enotes_api_service.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Boolean changePassword(PasswordChangeRequest changeRequest) {
        User loggedInUser = CommonUtil.getLoggedInUser();
        if(!passwordEncoder.matches(changeRequest.getOldPassword(),loggedInUser.getPassword())){
            throw new PasswordNotMatchedException("Old Password Incorrect");
        }
       loggedInUser.setPassword(passwordEncoder.encode(changeRequest.getNewPassword()));
       userRepository.save(loggedInUser);
        return true;
    }
}
