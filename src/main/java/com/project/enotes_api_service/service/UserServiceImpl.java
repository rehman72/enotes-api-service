package com.project.enotes_api_service.service;

import com.project.enotes_api_service.dto.EmailRequest;
import com.project.enotes_api_service.dto.UserDto;
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
    public Boolean register(UserDto userDto) throws Exception {
      validation.userValidation(userDto);
        User user = modelMapper.map(userDto, User.class);
        setRole(userDto,user);
        User save = userRepository.save(user);
        if(ObjectUtils.isEmpty(save)){
           return false;
        }
        mailSend(save);
        return true;
    }

    private void mailSend(User savedUser) throws Exception {

        String message="Hi,<b>"+ savedUser.getFirstName()+"</b>" +
                "<br> Your account register sucessfully<br>"+
                "<br> Click the Below Link and verify & Active Account your Account <br>"+
                "<a href='#'> Click Here </a> <br> <br>"+
                "Thanks,<br>Enotes.com";
        EmailRequest emailRequest=EmailRequest
                .builder()
                .to(savedUser.getEmail())
                .title("Account Creation Confirmation")
                .Subject("Account Created Successfully")
                .message(message)
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
