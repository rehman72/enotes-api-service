package com.project.enotes_api_service.Controller;

import com.project.enotes_api_service.Exception.ResourceNotFoundException;
import com.project.enotes_api_service.contoller.CategoryController;
import com.project.enotes_api_service.dto.CategoryDto;
import com.project.enotes_api_service.entity.Category;
import com.project.enotes_api_service.service.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    private Category category=null;

    private CategoryDto categoryDto=null;

    @BeforeEach
    public void initialSetup(){
        categoryDto= CategoryDto.builder()
                .id(null)
                .name("Java Category")
                .description("This is my Description")
                .build();
       category = Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .build();
    }



    @Test
    public void saveCategory() throws ResourceNotFoundException {
//        Arrange
        when(categoryService.saveCategory(categoryDto)).thenReturn(true);

//        Act
        ResponseEntity<?> responseEntity = categoryController.saveCategory(categoryDto);

        Object body = responseEntity.getBody();
        Map<String,String> bodyJson=(Map<String,String>)body;
//      Assert
        Assertions.assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
        Assertions.assertEquals("Success",bodyJson.get("status"));
        Assertions.assertEquals("saved Success",bodyJson.get("message"));

    }

    @Test
    public void saveCategoryNotSaved() throws ResourceNotFoundException {
//        Arrange
        when(categoryService.saveCategory(categoryDto)).thenReturn(false);

//        Act
        ResponseEntity<?> responseEntity = categoryController.saveCategory(categoryDto);

        Object body = responseEntity.getBody();
        Map<String,String> bodyJson=(Map<String,String>)body;
//      Assert
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,responseEntity.getStatusCode());
        Assertions.assertEquals("Failed",bodyJson.get("status"));
        Assertions.assertEquals("Error Saving Category",bodyJson.get("message"));

    }



}
