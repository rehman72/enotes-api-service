package com.project.enotes_api_service.contoller;

import com.project.enotes_api_service.dto.UserDto;
import com.project.enotes_api_service.service.UserService;
import com.project.enotes_api_service.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto){
        Boolean registered = userService.register(userDto);
        if(registered){
           return CommonUtil.createBuildResponseMessage("User Registered Success", HttpStatus.CREATED);
        }else{
            return CommonUtil.createErrorResponseMessage("Registration Failed!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
