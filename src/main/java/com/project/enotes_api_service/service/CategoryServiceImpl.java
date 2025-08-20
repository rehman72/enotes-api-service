package com.project.enotes_api_service.service;

import com.project.enotes_api_service.dto.CategoryDto;
import com.project.enotes_api_service.dto.CategoryResponseDto;
import com.project.enotes_api_service.entity.Category;
import com.project.enotes_api_service.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Boolean saveCategory(CategoryDto category) {
//        category.setIsDeleted(false);
//        category.setCreatedOn(LocalDateTime.now());
//        category.setCreateBy(1);
//       Category savedCategory= categoryRepository.save(category);
//       if(ObjectUtils.isEmpty(savedCategory)){
//          return false;
//       }
        Category category1 = modelMapper.map(category, Category.class);
        category1.setName(category.getName());
        category1.setDescription(category.getDescription());
        category1.setCreateBy(1);
        category1.setCreatedOn(LocalDateTime.now());
        category1.setIsActive(category.getIsActive());
        category1.setIsDeleted(false);
        Category saveCategory = categoryRepository.save(category1);
        return !ObjectUtils.isEmpty(saveCategory);
    }



    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> allCategories = categoryRepository
                .findAll();
         return  allCategories.
                stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                 .toList();
    }

    @Override
    public List<CategoryResponseDto> getActiveCategory() {
        List<Category> categoriesActive = categoryRepository.findByIsActiveTrue();
         return categoriesActive.stream()
                .map(category -> modelMapper.map(category, CategoryResponseDto.class))
                .toList();
    }

}
