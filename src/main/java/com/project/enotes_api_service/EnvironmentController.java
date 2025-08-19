package com.project.enotes_api_service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnvironmentController {

    @Value("${my.config}")
    private String envirnomenValue;

    @GetMapping("/")
    public ResponseEntity<?> getEnvironment(){
        return ResponseEntity.ok(envirnomenValue);
    }




}
