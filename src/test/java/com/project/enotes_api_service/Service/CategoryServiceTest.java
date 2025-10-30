package com.project.enotes_api_service.Service;

import com.project.enotes_api_service.Exception.AlreadyExistException;
import com.project.enotes_api_service.Exception.ResourceNotFoundException;
import com.project.enotes_api_service.Exception.ValidationException;
import com.project.enotes_api_service.dto.CategoryDto;
import com.project.enotes_api_service.entity.Category;
import com.project.enotes_api_service.repository.CategoryRepository;
import com.project.enotes_api_service.service.CategoryServiceImpl;
import com.project.enotes_api_service.util.Validation;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    public ModelMapper modelMapper;

    @InjectMocks
    private CategoryServiceImpl categoryImpl;

    private CategoryDto categoryDto=null;

     private Category category=null;

    @Spy
    private Validation validation;

    @BeforeEach
    public void initialize() {
        categoryDto=CategoryDto
                .builder()
                .id(null)
                .name("Java Notes")
                .description("This is Java Notes Description")
                .isActive(true)
                .build();

        category=new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setIsActive(categoryDto.getIsActive());

    }

    @Test
    public void testSaveCategory() throws ResourceNotFoundException {

//      Arrange
        when(categoryRepository.existsByName(categoryDto.getName())).thenReturn(false);
        when(modelMapper.map(categoryDto, Category.class)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);

//        Act
        Boolean isSaved = categoryImpl.saveCategory(categoryDto);

//        Assert
        assertTrue(isSaved);

//        verify
        verify(validation).CategoryValidation(categoryDto);
        verify(categoryRepository).existsByName(category.getName());
        verify(categoryRepository).save(category);

    }

    @Test
    public void  testCategoryExist(){
        when(categoryRepository.existsByName(categoryDto.getName())).thenReturn(true);
        AlreadyExistException alreadyExistException = assertThrows(AlreadyExistException.class, () -> categoryImpl.saveCategory(categoryDto));
        assertEquals("Category Already Exists",alreadyExistException.getMessage());
        verify(validation).CategoryValidation(categoryDto);
        verify(categoryRepository).existsByName(categoryDto.getName());
        verify(categoryRepository,never()).save(category);
    }

    @Test
    public void  testCategoryValidationDoesNotThrowError(){
        CategoryDto dto = CategoryDto.builder()
                .name("Java")
                .description("This is a valid description")
                .build();
        assertDoesNotThrow(()->validation.CategoryValidation(dto));
    }

    @Test
    public void testCategoryValidation_InvalidName_shouldThrow(){
        CategoryDto dto = CategoryDto.builder()
                .name("he")
                .description("This is a valid description")
                .build();

        ValidationException exception = assertThrows(ValidationException.class, () -> validation.CategoryValidation(dto));

        Map<String,Object> errors=exception.getError();
        assertTrue(errors.containsKey("name"));
        assertEquals("Name Field Must be at least 3 characters",errors.get("name"));
    }
    @Test
    public void testCategoryValidation_InvalidDescription_shouldThrow(){
        CategoryDto dto = CategoryDto.builder()
                .name("Hello")
                .description("Descrip")
                .build();

        ValidationException exception = assertThrows(ValidationException.class, () -> validation.CategoryValidation(dto));

        Map<String,Object> errors=exception.getError();
        assertTrue(errors.containsKey("description"));
        assertEquals("Description Field Must be minimum  10 to 30 characters",errors.get("description"));
    }

    @Test
    public void testCategoryValidation_InvalidId_shouldThrow(){
        CategoryDto dto = CategoryDto.builder()
                .id(40)
                .name("Hello")
                .description("Description")
                .build();
        assertDoesNotThrow(()->validation.CategoryValidation(dto));
        when(categoryRepository.existsByName(dto.getName())).thenReturn(false);
        when(modelMapper.map(dto,Category.class)).thenReturn(new Category(){{
            setId(dto.getId());
            setName(dto.getName());
            setDescription(dto.getDescription());
        }});
        when(categoryRepository.findById(dto.getId())).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> categoryImpl.saveCategory(dto));
        assertEquals("Category Not Found!",exception.getMessage());
    }

}
