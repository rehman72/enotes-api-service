package com.project.enotes_api_service.util;

import com.project.enotes_api_service.Enums.TodoStatus;
import com.project.enotes_api_service.Exception.AlreadyExistException;
import com.project.enotes_api_service.Exception.ValidationException;
import com.project.enotes_api_service.dto.CategoryDto;
import com.project.enotes_api_service.dto.TodoDto;
import com.project.enotes_api_service.dto.UserRequest;
import com.project.enotes_api_service.entity.Role;
import com.project.enotes_api_service.repository.RoleRepository;
import com.project.enotes_api_service.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class Validation {


    private final RoleRepository roleRepository;


    private final UserRepository userRepository;

    public Validation(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public void CategoryValidation(CategoryDto categoryDto){
        Map<String,Object> error=new LinkedHashMap<>();
        if(ObjectUtils.isEmpty(categoryDto)){
            throw new ValidationException("Pass Category object as {JSON} Body");
        }else{
            validateName(categoryDto,error);
            validateDescription(categoryDto,error);
        }
        if(!error.isEmpty()){
            throw new ValidationException(error);
        }
    }

    public void validateName(CategoryDto categoryDto,Map<String,Object> error){
        String name = categoryDto.getName();
        if(ObjectUtils.isEmpty(name)){
            error.put("name","Name Field Cannot be null");
        }else if (name.length()<3){
            error.put("name","Name Field Must be at least 3 characters");
        }
    }
    public void validateDescription(CategoryDto categoryDto,Map<String,Object> error){
        String description = categoryDto.getDescription();
        if(ObjectUtils.isEmpty(description)){
            error.put("description","Description Field Cannot be null");
        }else if(description.length()<10 || description.length()>25){
            error.put("description","Description Field Must be minimum  10 to 25 characters");
        }
    }

    public static void todoValidation(TodoDto todoDto){
        TodoDto.StatusDto status = todoDto.getStatus();
        boolean isValid=false;
        for(TodoStatus  statusDto:TodoStatus.values()){
            if(statusDto.getId().equals(status.getId())){
               isValid=true;
               break;
            }
        }
        if(!isValid){
            throw new ValidationException("Invalid Status");
        }
    }

    public void userValidation(UserRequest userDto) throws ValidationException{
        if(!StringUtils.hasText(userDto.getFirstName())){
            throw new ValidationException("User First Name is Invalid1");
        }
        if(!StringUtils.hasText(userDto.getLastName())){
            throw new ValidationException("last Name is Invalid!");
        }

        if(!StringUtils.hasText(userDto.getEmail()) || !userDto.getEmail().matches(Constants.emailRegex)){
            throw new ValidationException("Email is Invalid!");
        }else{
            boolean exists = userRepository.existsByEmail((userDto.getEmail()));
            if(exists){
                throw new AlreadyExistException("This Email is already Registered!");
            }
        }

        if(!StringUtils.hasText(userDto.getMobNo()) || !userDto.getMobNo().matches(Constants.mobileRegex)){
            throw new ValidationException("mob is Invalid");
        }

        if(CollectionUtils.isEmpty(userDto.getRoles())){
            throw  new ValidationException("Role Cannot be Empty!");
        }else{
            List<Integer> list = roleRepository.findAll()
                    .stream()
                    .map(Role::getId)
                    .toList();
            List<Integer> invalidRoleIds = userDto.getRoles().stream()
                    .map(UserRequest.RoleDto::getId)
                    .filter(ids->!list.contains(ids))
                    .toList();
            if(!CollectionUtils.isEmpty(invalidRoleIds)){
                throw new ValidationException("Invalid Role Id"+invalidRoleIds);
            }

        }


    }

}


