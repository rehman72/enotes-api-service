package com.project.enotes_api_service.Endpoint;

import com.project.enotes_api_service.dto.PasswordChangeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Tag(name = "User",description ="User Profiles Apis& Change-Password" )
@RequestMapping("/api/v1/user")
public interface UserEndPoint {

    @Operation(summary = "Get Current User Profile",tags = "User Profiles"
    )
    @GetMapping("/profile")
    ResponseEntity<?> getProfile();

    @Operation(summary = "Change-Password-current-password",description = "User Profiles")
    @PostMapping("/change-password")
    ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest);

}
