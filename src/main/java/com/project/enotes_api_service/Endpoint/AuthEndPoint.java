package com.project.enotes_api_service.Endpoint;

import com.project.enotes_api_service.dto.LoginRequest;
import com.project.enotes_api_service.dto.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "User Authentication",description = "ALl the User Authentication Apis")
@RequestMapping("/api/v1/auth")
public interface AuthEndPoint {
    @Operation(summary = "Register User",tags = {"User Authentication","Auth"})
    @ApiResponses(value = {@ApiResponse(responseCode = "201",description ="User Registered Successfully"),
    @ApiResponse(responseCode = "500",description = "Registration Failed Try Again!"),
            @ApiResponse(responseCode = "409",description = "User Already Registered!"),
            @ApiResponse(responseCode = "404",description = "Not Found!"),
            @ApiResponse(responseCode = "400",description = "Validation Failed!")
    }
    )
    @PostMapping("/register")
    ResponseEntity<?> registerUser(@RequestBody UserRequest userDto, HttpServletRequest serverRequest) throws Exception;

    @Operation(summary = "User Login",tags = {"User Authentication","Auth"})
    @PostMapping("/login")
    ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest);
}
