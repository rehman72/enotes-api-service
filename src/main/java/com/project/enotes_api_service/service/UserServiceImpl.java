package com.project.enotes_api_service.service;

import com.project.enotes_api_service.dto.EmailRequest;
import com.project.enotes_api_service.dto.UserDto;
import com.project.enotes_api_service.entity.AccountStatus;
import com.project.enotes_api_service.entity.Role;
import com.project.enotes_api_service.entity.User;
import com.project.enotes_api_service.repository.RoleRepository;
import com.project.enotes_api_service.repository.UserRepository;
import com.project.enotes_api_service.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Validation validation;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailSendService emailSendService;

    @Override
    @Transactional
    public Boolean register(UserDto userDto, String url) throws Exception {
      validation.userValidation(userDto);
        User user = modelMapper.map(userDto, User.class);
        setRole(userDto,user);
        AccountStatus accountStatus= AccountStatus
                .builder()
                .isActive(false)
                .verificationCode(UUID.randomUUID().toString())
                .build();
        user.setAccountStatus(accountStatus);
        User save = userRepository.save(user);
        if(ObjectUtils.isEmpty(save)) {
            return false;
        }
        mailSend(save,url);
        return true;
    }

    private void mailSend(User savedUser,String requestUrl) throws Exception {
        String mailSendBody="Hi,<b>"+"[[username]]"+"</b>" +
                "<br> Your account register successfully<br>"+
                "<br> Click the Below Link and verify & Active your Account <br>"+
                "<a href='[[url]]'>Click Here </a> <br> <br>"+
                "Thanks,<br>Enotes.com";

        mailSendBody=mailSendBody.replace("[[username]]",savedUser.getFirstName());
        mailSendBody=mailSendBody.replace("[[url]]", requestUrl+"/api/v1/Home/verify?id="+savedUser.getId()
                        +"&&verificationCode="+savedUser.getAccountStatus().getVerificationCode());
        EmailRequest emailRequest=EmailRequest
                .builder()
                .to(savedUser.getEmail())
                .title("Account Creation Confirmation")
                .Subject("Account Created Successfully")
                .message(mailSendBody)
                .build();

        emailSendService.send(emailRequest);
    }

    private void setRole(UserDto userDto, User user) {
        List<Integer> requestIds = userDto.getRoles()
                .stream()
                .map(userIds -> userIds.getId())
                .toList();

        List<Role> allRole  = roleRepository.findAllById(requestIds);

        user.setRoles(allRole);

    }
}
