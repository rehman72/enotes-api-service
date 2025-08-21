package com.project.enotes_api_service.service;

import com.project.enotes_api_service.dto.CategoryDto;
import com.project.enotes_api_service.dto.CategoryResponseDto;
import java.util.List;

public interface CategoryService {

    Boolean saveCategory(CategoryDto category);

    List<CategoryDto> getAllCategory();

    List<CategoryResponseDto> getActiveCategory();

    CategoryDto getCategoryById(Integer id);

    boolean deleteCategory(Integer id);
}
