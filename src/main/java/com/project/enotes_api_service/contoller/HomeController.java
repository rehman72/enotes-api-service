package com.project.enotes_api_service.contoller;

import com.project.enotes_api_service.dto.PswdResetRequest;
import com.project.enotes_api_service.service.HomeServiceImpl;
import com.project.enotes_api_service.service.UserService;
import com.project.enotes_api_service.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/Home")
public class HomeController {

    @Autowired
    private HomeServiceImpl homeService;

    @Autowired
    private UserService userService;



    @GetMapping("/verify")
    public ResponseEntity<?> verifyAccount(@RequestParam Integer id ,@RequestParam String verificationCode) throws Exception {
        Boolean isVerified = homeService.verifyAccount(id, verificationCode);
        if(isVerified){
            return CommonUtil.createBuildResponseMessage("Account Verified Success", HttpStatus.OK);
        }
        return CommonUtil.createErrorResponseMessage("Account Verification Failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/send-email-reset")
    public ResponseEntity<?> sendEmailForForPasswordReset(@RequestParam String email, HttpServletRequest httpServletRequest) throws Exception{
        String url = CommonUtil.getUrl(httpServletRequest);
        Boolean isSend=userService.sendEmailPasswordReset(email,url);
        if(!isSend){
            return CommonUtil.createErrorResponseMessage("Password Reset Link Not Sent! ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return CommonUtil.createBuildResponseMessage("Password Reset Link Sent! Check Email for Reset", HttpStatus.OK);
    }

    @GetMapping("/verify-pswd-link")
    public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid,@RequestParam String passResetToken) throws Exception{
        Boolean isVerified = userService.verifyPasswordLink(uid, passResetToken);
        if (isVerified) {
            return CommonUtil.createBuildResponseMessage("Password Reset Link Verified! ", HttpStatus.OK);
        }
        return CommonUtil.createErrorResponseMessage("Verify Link Error!", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @PostMapping("/reset-pswd")
    public ResponseEntity<?> resetPassword(@RequestBody PswdResetRequest passwordResetRequest) throws Exception {
        userService.resetPassword(passwordResetRequest);
    return CommonUtil.createBuildResponseMessage("Password Reset Success", HttpStatus.OK);
    }

}
