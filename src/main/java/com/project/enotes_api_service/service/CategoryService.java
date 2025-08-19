package com.project.enotes_api_service.service;

import com.project.enotes_api_service.entity.Category;

import java.util.List;

public interface CategoryService {

    Boolean saveCategory(Category category);

    List<Category> getAllCategory();

}
