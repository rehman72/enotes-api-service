package com.project.enotes_api_service.contoller;

import com.project.enotes_api_service.dto.CategoryDto;
import com.project.enotes_api_service.dto.CategoryResponseDto;
import com.project.enotes_api_service.service.CategoryServiceImpl;
import com.project.enotes_api_service.util.CommonUtil;
import com.project.enotes_api_service.util.Validation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private  CategoryServiceImpl categoryService;

    @Autowired
    private Validation validation;

    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto category){
        Boolean isSaved = categoryService.saveCategory(category);
        validation.CategoryValidation(category);
        if(isSaved){
          return  CommonUtil.createBuildResponseMessage("saved Success",HttpStatus.CREATED);
//            return new ResponseEntity<>("Saved Success",HttpStatus.CREATED);
        }else{
            return CommonUtil.createErrorResponseMessage("Error Saving Category",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/Category")
    public ResponseEntity<?> getAllCategory(){
        List<CategoryDto> allCategories = categoryService.getAllCategory();
        if(CollectionUtils.isEmpty(allCategories)){
            return new ResponseEntity<>("No Category Found",HttpStatus.NO_CONTENT);
        }else{
            return CommonUtil.createBuildResponse(allCategories,HttpStatus.OK);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_USER') && hasAuthority('ROLE_ADMIN')")
    @GetMapping("/active-Category")
    public ResponseEntity<?> getActiveCategory(){
        List<CategoryResponseDto> allCategories = categoryService.getActiveCategory();
        if(CollectionUtils.isEmpty(allCategories)){
//            return new ResponseEntity<>("No Category Found",HttpStatus.NO_CONTENT);
            return CommonUtil.createErrorResponseMessage("No Category Found",HttpStatus.NO_CONTENT);
        }else{
//            return new  ResponseEntity<>(allCategories,HttpStatus.OK);
            return CommonUtil.createBuildResponse(allCategories,HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Integer id) throws Exception {
        CategoryDto categoryById = categoryService.getCategoryById(id);
        if(ObjectUtils.isEmpty(categoryById)){
//            return new ResponseEntity<>("INTERNAL SERVER ERROR",HttpStatus.NOT_FOUND);
            return CommonUtil.createErrorResponseMessage("Category Not Found with Id",HttpStatus.NOT_FOUND);
        }
//        return new ResponseEntity<>(categoryById,HttpStatus.OK);
            return CommonUtil.createBuildResponse(categoryById,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable("id") Integer id){
        boolean isDeleted = categoryService.deleteCategory(id);
        if(isDeleted){
//            return new ResponseEntity<>("Category Deleted Successfully!",HttpStatus.OK);
            return CommonUtil.createBuildResponseMessage("Category Deleted Successfully",HttpStatus.OK);
        }else{
//            return new ResponseEntity<>("Category Not Found!",HttpStatus.NOT_FOUND);
        return  CommonUtil.createErrorResponseMessage("Category Not Found",HttpStatus.NOT_FOUND);
            }

    }





}
