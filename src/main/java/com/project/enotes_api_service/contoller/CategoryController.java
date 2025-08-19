package com.project.enotes_api_service.contoller;

import com.project.enotes_api_service.entity.Category;
import com.project.enotes_api_service.service.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private  CategoryServiceImpl categoryService;


    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody Category category){
        Boolean isSaved = categoryService.saveCategory(category);
        if(isSaved){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }else{
            return new ResponseEntity<>("not saved",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/Category")
    public ResponseEntity<?> getAllCategory(){
        List<Category> allCategories = categoryService.getAllCategory();
        if(CollectionUtils.isEmpty(allCategories)){
            return new ResponseEntity<>("No Category Found",HttpStatus.NO_CONTENT);
        }else{
            return new  ResponseEntity<>(allCategories,HttpStatus.OK);
        }
    }





}
