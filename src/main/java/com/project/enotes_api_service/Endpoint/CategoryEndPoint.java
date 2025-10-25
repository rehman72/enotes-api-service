package com.project.enotes_api_service.Endpoint;

import com.project.enotes_api_service.Exception.ResourceNotFoundException;
import com.project.enotes_api_service.dto.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import static com.project.enotes_api_service.util.Constants.ROLE_USER;
import static com.project.enotes_api_service.util.Constants.ROLE_ADMIN_USER;

@Tag(name = "Category Apis's",description = "Category Apis")
@RequestMapping("/api/v1/category")
public interface CategoryEndPoint {

    @Operation(summary = "Save Category",tags = {"Category Apis's","User"})
    @PostMapping("/save-category")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> saveCategory(@RequestBody CategoryDto category);

    @Operation(summary = "Get All Category",description = "Only for Admin",tags = {"Category Apis's"})
    @PreAuthorize(ROLE_ADMIN_USER)
    @GetMapping("/")
    ResponseEntity<?> getAllCategory();

    @Operation(summary = "Get All Active Category",description = "User && Admin",tags = {"Category Apis's"})
    @PreAuthorize(ROLE_ADMIN_USER)
    @GetMapping("/active-Category")
    ResponseEntity<?> getActiveCategory();

    @Operation(summary = "Get Category ById",description = "Only for Admin",tags = {"Category Apis's"})
    @GetMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN_USER)
    ResponseEntity<?> getCategoryById(@PathVariable("id") Integer id) throws Exception;

    @Operation(summary = "Delete CategoryById",description = "Only for Admin",tags = {"Category Apis's"})
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteCategoryById(@PathVariable("id") Integer id) throws ResourceNotFoundException;
}
