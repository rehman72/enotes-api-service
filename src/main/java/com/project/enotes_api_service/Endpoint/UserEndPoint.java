package com.project.enotes_api_service.Endpoint;

import com.project.enotes_api_service.dto.PasswordChangeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/user")
public interface UserEndPoint {

    @RequestMapping("/profile")
    ResponseEntity<?> getProfile();

    @PostMapping("/forgot-password")
    ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest);

}
