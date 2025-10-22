package com.project.enotes_api_service.contoller;

import com.project.enotes_api_service.Endpoint.HomeEndPoint;
import com.project.enotes_api_service.dto.PswdResetRequest;
import com.project.enotes_api_service.service.HomeServiceImpl;
import com.project.enotes_api_service.service.UserService;
import com.project.enotes_api_service.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class HomeController implements HomeEndPoint {

    Logger log=LoggerFactory.getLogger(HomeController.class);


    private final HomeServiceImpl homeService;


    private final UserService userService;

    public HomeController(HomeServiceImpl homeService, UserService userService) {
        this.homeService = homeService;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<?> verifyAccount(Integer id ,String verificationCode) throws Exception {
        log.info("HomeController : verifyAccount() : Execution Started");
        Boolean isVerified = homeService.verifyAccount(id, verificationCode);
        if(isVerified)
            return CommonUtil.createBuildResponseMessage("Account Verified Success", HttpStatus.OK);
        log.info("HomeController : verifyAccount : Execution End");
        return CommonUtil.createErrorResponseMessage("Account Verification Failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<?> sendEmailForPasswordReset(String email, HttpServletRequest httpServletRequest) throws Exception{
        String url = CommonUtil.getUrl(httpServletRequest);
        Boolean isSend=userService.sendEmailPasswordReset(email,url);
        if(!isSend){
            return CommonUtil.createErrorResponseMessage("Password Reset Link Not Sent! ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return CommonUtil.createBuildResponseMessage("Password Reset Link Sent! Check Email for Reset", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> verifyPasswordResetLink(Integer uid,String passResetToken) throws Exception{
        Boolean isVerified = userService.verifyPasswordLink(uid, passResetToken);
        if (isVerified) {
            return CommonUtil.createBuildResponseMessage("Password Reset Link Verified! ", HttpStatus.OK);
        }
        return CommonUtil.createErrorResponseMessage("Verify Link Error!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> resetPassword(PswdResetRequest passwordResetRequest) throws Exception {
        userService.resetPassword(passwordResetRequest);
    return CommonUtil.createBuildResponseMessage("Password Reset Success", HttpStatus.OK);
    }

}
