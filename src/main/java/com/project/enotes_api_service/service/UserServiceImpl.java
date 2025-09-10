package com.project.enotes_api_service.service;

import com.project.enotes_api_service.dto.UserDto;
import com.project.enotes_api_service.entity.Role;
import com.project.enotes_api_service.entity.User;
import com.project.enotes_api_service.repository.RoleRepository;
import com.project.enotes_api_service.repository.UserRepository;
import com.project.enotes_api_service.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Override
    public Boolean register(UserDto userDto) {
      validation.userValidation(userDto);
        User user = modelMapper.map(userDto, User.class);
        setRole(userDto,user);
        User save = userRepository.save(user);
        if(ObjectUtils.isEmpty(save)){
           return false;
        }
        return true;
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
