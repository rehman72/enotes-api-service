package com.project.enotes_api_service.Endpoint;

import com.project.enotes_api_service.dto.PswdResetRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth",description = "All Open Apis's & Auth")
@RequestMapping("/api/v1/Home")
public interface HomeEndPoint {

     @Operation(summary = "Verify Account")
     @GetMapping("/verify")
     ResponseEntity<?> verifyAccount(@RequestParam Integer id , @RequestParam String verificationCode) throws Exception;

     @Operation(summary = "Sending Email Reset")
     @GetMapping("/forgot-password")
     ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest httpServletRequest) throws Exception;

     @Operation(summary = "verifying Password Reset Link")
     @GetMapping("/verify-pswd-link")
     ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid,@RequestParam String passResetToken) throws Exception;

     @Operation(summary = "Reset Password")
     @PostMapping("/reset-pswd")
     ResponseEntity<?> resetPassword(@RequestBody PswdResetRequest passwordResetRequest) throws Exception;
}
