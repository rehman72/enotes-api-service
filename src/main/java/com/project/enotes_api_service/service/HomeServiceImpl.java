package com.project.enotes_api_service.service;

import com.project.enotes_api_service.Exception.ResourceNotFoundException;
import com.project.enotes_api_service.Exception.SuccessException;
import com.project.enotes_api_service.entity.AccountStatus;
import com.project.enotes_api_service.entity.User;
import com.project.enotes_api_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HomeServiceImpl implements HomeService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Boolean verifyAccount(Integer userId, String verificationCode) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        AccountStatus accountStatus = user.getAccountStatus();
        if(accountStatus.getVerificationCode() == null){
            throw new SuccessException("Already Verified");
        }
        if(accountStatus.getVerificationCode().equals(verificationCode)){
           accountStatus.setIsActive(true);
           accountStatus.setVerificationCode(null);
           userRepository.save(user);
           return true;
       }
        return false;
    }
}
