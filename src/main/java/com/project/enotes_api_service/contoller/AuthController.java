package com.project.enotes_api_service.contoller;

import com.project.enotes_api_service.Endpoint.AuthEndPoint;
import com.project.enotes_api_service.Exception.RegisterationException;
import com.project.enotes_api_service.dto.LoginRequest;
import com.project.enotes_api_service.dto.LoginResponse;
import com.project.enotes_api_service.dto.UserRequest;
import com.project.enotes_api_service.service.AuthService;
import com.project.enotes_api_service.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthEndPoint {

    private final AuthService authService;

    Logger log= LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public ResponseEntity<?> registerUser(UserRequest userDto, HttpServletRequest serverRequest) throws RegisterationException {
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

    public ResponseEntity<?> loginUser(LoginRequest loginRequest)  {
       LoginResponse loginResponse= authService.login(loginRequest);
       if(ObjectUtils.isEmpty(loginResponse)){
           return CommonUtil.createErrorResponseMessage("Login Failed!", HttpStatus.INTERNAL_SERVER_ERROR);
       }
       return CommonUtil.createBuildResponse(loginResponse,HttpStatus.OK);
    }

}
