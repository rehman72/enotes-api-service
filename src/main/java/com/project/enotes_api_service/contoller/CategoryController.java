package com.project.enotes_api_service.contoller;

import com.project.enotes_api_service.dto.CategoryDto;
import com.project.enotes_api_service.dto.CategoryResponseDto;
import com.project.enotes_api_service.service.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private  CategoryServiceImpl categoryService;


    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto category){
        Boolean isSaved = categoryService.saveCategory(category);
        if(isSaved){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }else{
            return new ResponseEntity<>("not saved",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/Category")
    public ResponseEntity<?> getAllCategory(){
        List<CategoryDto> allCategories = categoryService.getAllCategory();
        if(CollectionUtils.isEmpty(allCategories)){
            return new ResponseEntity<>("No Category Found",HttpStatus.NO_CONTENT);
        }else{
            return new  ResponseEntity<>(allCategories,HttpStatus.OK);
        }
    }

    @GetMapping("/active-Category")
    public ResponseEntity<?> getActiveCategory(){
        List<CategoryResponseDto> allCategories = categoryService.getActiveCategory();
        if(CollectionUtils.isEmpty(allCategories)){
            return new ResponseEntity<>("No Category Found",HttpStatus.NO_CONTENT);
        }else{
            return new  ResponseEntity<>(allCategories,HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Integer id){
        CategoryDto categoryById = categoryService.getCategoryById(id);
        if(ObjectUtils.isEmpty(categoryById)){
            return new ResponseEntity<>("Category Not Found",HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(categoryById,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable("id") Integer id){
        boolean isDeleted = categoryService.deleteCategory(id);
        if(isDeleted){
            return new ResponseEntity<>("Category Deleted Successfully!",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Category Not Found!",HttpStatus.NOT_FOUND);
        }

    }





}
