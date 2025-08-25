package com.project.enotes_api_service.service;

import com.project.enotes_api_service.Exception.ResourceNotFoundException;
import com.project.enotes_api_service.dto.CategoryDto;
import com.project.enotes_api_service.dto.CategoryResponseDto;
import com.project.enotes_api_service.entity.Category;
import com.project.enotes_api_service.repository.CategoryRepository;
import com.project.enotes_api_service.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private Validation validation;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    @Override
    public Boolean saveCategory(CategoryDto category) {
//        category.setIsDeleted(false);
//        category.setCreatedOn(LocalDateTime.now());
//        category.setCreateBy(1);
//       Category savedCategory= categoryRepository.save(category);
//       if(ObjectUtils.isEmpty(savedCategory)){
//          return false;
//       }
        validation.CategoryValidation(category);
        Category category1 = modelMapper.map(category, Category.class);
        if(ObjectUtils.isEmpty(category1.getId())){
            category1.setIsDeleted(false);
            category1.setCreatedOn(LocalDateTime.now());
            category1.setCreateBy(1);
        }else{
            updateCategory(category1);
        }
        categoryRepository.save(category1);
        return true;
    }

    private void updateCategory(Category category) {
        Optional<Category> categoryById = categoryRepository.findById(category.getId());
        if(categoryById.isPresent()){
            Category optionalcategory = categoryById.get();
            category.setUpdatedBy(1);
            category.setUpdatedOn(LocalDateTime.now());
            category.setIsDeleted(optionalcategory.getIsDeleted());
            category.setCreatedOn(optionalcategory.getCreatedOn());
            category.setCreateBy(optionalcategory.getCreateBy());
            }
    }


    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> allCategories = categoryRepository
                .findAllAndIsDeletedFalse();
         return  allCategories.
                stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                 .toList();
    }

    @Override
    public List<CategoryResponseDto> getActiveCategory() {
        List<Category> categoriesActive = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
         return categoriesActive.stream()
                .map(category -> modelMapper.map(category, CategoryResponseDto.class))
                .toList();
    }

    @Override
    public CategoryDto  getCategoryById(Integer id) throws Exception {
        Category category=null;
            category = categoryRepository.findByIdAndIsDeletedFalse(id)
                    .orElseThrow(()->new ResourceNotFoundException("Category not found with id "+id));
        if(!ObjectUtils.isEmpty(category)){
           return modelMapper.map(category, CategoryDto.class);
        }
        return  null;
    }

    @Override
    public boolean deleteCategory(Integer id) {
        Optional<Category> byId = categoryRepository.findById(id);
        if(byId.isPresent()){
            Category category = byId.get();
            category.setIsDeleted(true);
            categoryRepository.save(category);
            return true;
        }
        return false;
    }
}
