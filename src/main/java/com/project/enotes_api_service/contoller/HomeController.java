package com.project.enotes_api_service.contoller;

import com.project.enotes_api_service.service.HomeServiceImpl;
import com.project.enotes_api_service.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/Home")
public class HomeController {

    @Autowired
    private HomeServiceImpl homeService;

    @GetMapping("/verify")
    public ResponseEntity<?> verifyAccount(@RequestParam Integer id ,@RequestParam String verificationCode) throws Exception {
        Boolean isVerified = homeService.verifyAccount(id, verificationCode);
        if(isVerified){
            return CommonUtil.createBuildResponseMessage("Account Verified Success", HttpStatus.OK);
        }
        return CommonUtil.createErrorResponseMessage("Account Verification Failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
