package com.project.enotes_api_service.Endpoint;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@Tag(name = "Caching EndPoints",description = "All Caching EndPoints")
@RequestMapping("/api/v1/cache")
public interface CacheEndPoint {

    @Operation(summary = "Get AllCache")
    @GetMapping("/")
    ResponseEntity<?> getCallCache();

    @Operation(summary = "Get Single Cache")
    @GetMapping("/{cacheName}")
    ResponseEntity<?> getCache(@PathVariable String cacheName);

    @Operation(summary = "Clear ALl Cache")
    @DeleteMapping("/delete")
    ResponseEntity<?> clearAllCache();

}
