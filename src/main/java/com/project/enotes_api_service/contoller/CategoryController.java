package com.project.enotes_api_service.contoller;

import com.project.enotes_api_service.Endpoint.CategoryEndPoint;
import com.project.enotes_api_service.Exception.ResourceNotFoundException;
import com.project.enotes_api_service.dto.CategoryDto;
import com.project.enotes_api_service.dto.CategoryResponseDto;
import com.project.enotes_api_service.service.CategoryServiceImpl;
import com.project.enotes_api_service.util.CommonUtil;
import com.project.enotes_api_service.util.Validation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
public class CategoryController implements CategoryEndPoint {

    private final CategoryServiceImpl categoryService;

    private final Validation validation;

    public CategoryController(CategoryServiceImpl categoryService, Validation validation) {
        this.categoryService = categoryService;
        this.validation = validation;
    }

    public ResponseEntity<?> saveCategory(CategoryDto category){
        Boolean isSaved = categoryService.saveCategory(category);
        validation.CategoryValidation(category);
        if(isSaved){
          return  CommonUtil.createBuildResponseMessage("saved Success",HttpStatus.CREATED);
        }else{
            return CommonUtil.createErrorResponseMessage("Error Saving Category",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAllCategory(){
        List<CategoryDto> allCategories = categoryService.getAllCategory();
        if(CollectionUtils.isEmpty(allCategories)){
            return new ResponseEntity<>("No Category Found",HttpStatus.NO_CONTENT);
        }else{
            return CommonUtil.createBuildResponse(allCategories,HttpStatus.OK);
        }
    }

    public ResponseEntity<?> getActiveCategory(){
        List<CategoryResponseDto> allCategories = categoryService.getActiveCategory();
        if(CollectionUtils.isEmpty(allCategories)){
            return CommonUtil.createErrorResponseMessage("No Category Found",HttpStatus.NO_CONTENT);
        }else{
            return CommonUtil.createBuildResponse(allCategories,HttpStatus.OK);
        }
    }

    public ResponseEntity<?> getCategoryById(Integer id) throws Exception {
        CategoryDto categoryById = categoryService.getCategoryById(id);
        if(ObjectUtils.isEmpty(categoryById)){
            return CommonUtil.createErrorResponseMessage("Category Not Found with Id",HttpStatus.NOT_FOUND);
        }
            return CommonUtil.createBuildResponse(categoryById,HttpStatus.OK);
    }

    public ResponseEntity<?> deleteCategoryById(Integer id) throws ResourceNotFoundException {
        boolean isDeleted = categoryService.deleteCategory(id);
        if(isDeleted){
            return CommonUtil.createBuildResponseMessage("Category Deleted Successfully",HttpStatus.OK);
        }else{
        return  CommonUtil.createErrorResponseMessage("Category Not Found",HttpStatus.NOT_FOUND);
            }
    }

}
