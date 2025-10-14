package com.project.enotes_api_service.service;

import com.project.enotes_api_service.Exception.LinkExpiredException;
import com.project.enotes_api_service.Exception.PasswordNotMatchedException;
import com.project.enotes_api_service.Exception.ResourceNotFoundException;
import com.project.enotes_api_service.dto.EmailRequest;
import com.project.enotes_api_service.dto.PasswordChangeRequest;
import com.project.enotes_api_service.dto.PswdResetRequest;
import com.project.enotes_api_service.entity.User;
import com.project.enotes_api_service.repository.UserRepository;
import com.project.enotes_api_service.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSendService emailSendService;


    @Override
    public Boolean changePassword(PasswordChangeRequest changeRequest) {
        User loggedInUser = CommonUtil.getLoggedInUser();
        if(!passwordEncoder.matches(changeRequest.getOldPassword(), loggedInUser.getPassword())){
            throw new PasswordNotMatchedException("Old Password Incorrect");
        }
       loggedInUser.setPassword(passwordEncoder.encode(changeRequest.getNewPassword()));
       userRepository.save(loggedInUser);
        return true;
    }

    @Override
    public Boolean sendEmailPasswordReset(String email,String url) throws Exception {
        User user = userRepository.findByEmail(email);
        if(ObjectUtils.isEmpty(user)){
            throw new ResourceNotFoundException("Invalid Email");
        }
        user.getAccountStatus().setPasswordResetToken(UUID.randomUUID().toString());
        User updateduser = userRepository.save(user);
        sendMailReset(updateduser,url);
        return true;
    }

    @Override
    public Boolean verifyPasswordLink(Integer uid, String passResetToken) throws Exception {
        User user = userRepository.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + uid));
        if(StringUtils.hasText(passResetToken)){
//           Check Link Already Clicked or Not
            if(ObjectUtils.isEmpty(user.getAccountStatus().getPasswordResetToken())){
                throw new LinkExpiredException("Link Expired.");
            }
//            Check Token Match With DB
            boolean resetTokenMatch = user.getAccountStatus().getPasswordResetToken().equals(passResetToken);
            if(!resetTokenMatch){
                throw new IllegalArgumentException("Invalid Token");
            }
            userRepository.save(user);
        }else{
            return false;
        }
        return true;
    }

    @Override
    public void resetPassword(PswdResetRequest passwordResetRequest) throws Exception {
        User user = userRepository.findById(passwordResetRequest.getUid())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + passwordResetRequest.getUid()));
        String encode = passwordEncoder.encode(passwordResetRequest.getNewPassword());
        user.setPassword(encode);
        user.getAccountStatus().setPasswordResetToken(null);
        userRepository.save(user);
    }

    private void sendMailReset(User user,String requestUrl) throws Exception {
        String mailSendBody="Hi,<b>"+"[[username]]"+"</b>" +
                "<br> Password  Reset <br>"+
                "<br> Click the Below Link Verify and Change password <br>"+
                "<a href='[[url]]'>Click Here </a> <br> <br>"+
                "Thanks,<br>Enotes.com";

        mailSendBody=mailSendBody.replace("[[username]]",user.getFirstName());
        mailSendBody=mailSendBody.replace("[[url]]", requestUrl+"/api/v1/Home/verify-pswd-link"
                +"?uid="+user.getId()+"&passResetToken="+user.getAccountStatus().getPasswordResetToken());
        EmailRequest emailRequest=EmailRequest
                .builder()
                .to(user.getEmail())
                .title("Password Reset")
                .Subject("Password Reset Link")
                .message(mailSendBody)
                .build();

        emailSendService.send(emailRequest);
    }


}
