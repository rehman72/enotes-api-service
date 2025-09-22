package com.project.enotes_api_service.contoller;

import com.project.enotes_api_service.dto.UserResponse;
import com.project.enotes_api_service.entity.User;
import com.project.enotes_api_service.util.CommonUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping("/profile")
    public ResponseEntity<?> getProfile(){
        User loggedInUser = CommonUtil.getLoggedInUser();
        UserResponse UserResponseDto = modelMapper.map(loggedInUser, UserResponse.class);
        return CommonUtil.createBuildResponse(UserResponseDto, HttpStatus.OK);
    }
}
