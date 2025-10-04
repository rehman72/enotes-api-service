package com.project.enotes_api_service.contoller;

import com.project.enotes_api_service.dto.LoginRequest;
import com.project.enotes_api_service.dto.LoginResponse;
import com.project.enotes_api_service.dto.UserRequest;
import com.project.enotes_api_service.service.AuthService;
import com.project.enotes_api_service.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userDto, HttpServletRequest serverRequest) throws Exception {
        log.info("AuthController : registerUser() : Execution Started");
        String url = CommonUtil.getUrl(serverRequest);
        Boolean registered = authService.register(userDto,url);
        if(!registered){
            log.info("error: Registration Failed!");
            return CommonUtil.createErrorResponseMessage("Registration Failed!", HttpStatus.INTERNAL_SERVER_ERROR);
        }else{
            log.info("AuthController : registerUser() : Execution End");
            return CommonUtil.createBuildResponseMessage("User Registered Success", HttpStatus.CREATED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest)  {
       LoginResponse loginResponse= authService.login(loginRequest);
       if(ObjectUtils.isEmpty(loginResponse)){
           return CommonUtil.createErrorResponseMessage("Login Failed!", HttpStatus.INTERNAL_SERVER_ERROR);
       }
       return CommonUtil.createBuildResponse(loginResponse,HttpStatus.OK);
    }

}
