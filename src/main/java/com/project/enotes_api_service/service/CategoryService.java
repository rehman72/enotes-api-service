package com.project.enotes_api_service.service;

import com.project.enotes_api_service.Exception.ResourceNotFoundException;
import com.project.enotes_api_service.dto.CategoryDto;
import com.project.enotes_api_service.dto.CategoryResponseDto;
import java.util.List;

public interface CategoryService {

    Boolean saveCategory(CategoryDto category) throws ResourceNotFoundException;

    List<CategoryDto> getAllCategory();

    List<CategoryResponseDto> getActiveCategory();

    CategoryDto getCategoryById(Integer id) throws Exception;

    boolean deleteCategory(Integer id) throws ResourceNotFoundException;
}
