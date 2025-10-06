package com.project.enotes_api_service.Endpoint;

import com.project.enotes_api_service.dto.PswdResetRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/Home")
public interface HomeEndPoint {

     @GetMapping("/verify")
     ResponseEntity<?> verifyAccount(@RequestParam Integer id , @RequestParam String verificationCode) throws Exception;

     @GetMapping("/send-email-reset")
     ResponseEntity<?> sendEmailForForPasswordReset(@RequestParam String email, HttpServletRequest httpServletRequest) throws Exception;

     @GetMapping("/verify-pswd-link")
     ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid,@RequestParam String passResetToken) throws Exception;

     @PostMapping("/reset-pswd")
     ResponseEntity<?> resetPassword(@RequestBody PswdResetRequest passwordResetRequest) throws Exception;
}
