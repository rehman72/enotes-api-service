package com.project.enotes_api_service.Endpoint;

import com.project.enotes_api_service.dto.CategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import static com.project.enotes_api_service.util.Constants.ROLE_ADMIN;
import static com.project.enotes_api_service.util.Constants.ROLE_USER;
import static com.project.enotes_api_service.util.Constants.ROLE_ADMIN_USER;

@RequestMapping("/api/v1/category")
public interface CategoryEndPoint {

    @PostMapping("/save-category")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> saveCategory(@RequestBody CategoryDto category);

    @PreAuthorize(ROLE_ADMIN)
    @GetMapping("/")
    ResponseEntity<?> getAllCategory();

    @PreAuthorize(ROLE_ADMIN_USER)
    @GetMapping("/active-Category")
    ResponseEntity<?> getActiveCategory();

    @GetMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> getCategoryById(@PathVariable("id") Integer id) throws Exception;

    @DeleteMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> deleteCategoryById(@PathVariable("id") Integer id);
}
