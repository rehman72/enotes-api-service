package com.project.enotes_api_service.service;

import com.project.enotes_api_service.Exception.AlreadyExistException;
import com.project.enotes_api_service.Exception.ResourceNotFoundException;
import com.project.enotes_api_service.dto.CategoryDto;
import com.project.enotes_api_service.dto.CategoryResponseDto;
import com.project.enotes_api_service.entity.Category;
import com.project.enotes_api_service.repository.CategoryRepository;
import com.project.enotes_api_service.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final Validation validation;

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    private final CacheManagerService cacheManagerService;

    public CategoryServiceImpl(Validation validation, CategoryRepository categoryRepository, ModelMapper modelMapper,CacheManagerService cacheManagerService) {
        this.validation = validation;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.cacheManagerService = cacheManagerService;
    }

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
        Boolean isExists = categoryRepository.existsByName(category.getName());
        if(isExists){
            throw new  AlreadyExistException("Category Already Exists");
        }
        validation.CategoryValidation(category);
        Category category1 = modelMapper.map(category, Category.class);
        if(ObjectUtils.isEmpty(category1.getId())){
            category1.setIsDeleted(false);
        }else{
            updateCategory(category1);
        }
        categoryRepository.save(category1);
        return true;
    }

    private void updateCategory(Category category) {
        Optional<Category> categoryById = categoryRepository.findById(category.getId());
        categoryById.ifPresent(optionalcategory -> category.setIsDeleted(optionalcategory.getIsDeleted()));
    }


    @Override
    @Cacheable(value = "allCategory",unless = "#result == null or #result.isEmpty()")
    public List<CategoryDto> getAllCategory() {
        List<Category> allCategories = categoryRepository
                .findByIsDeletedFalse();
         return  allCategories.
                stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                 .toList();
    }

    @Override
    @Cacheable(value = "activeCategory",unless = "#result==null or #result.isEmpty()")
    public List<CategoryResponseDto> getActiveCategory() {
        List<Category> categoriesActive = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
         return categoriesActive.stream()
                .map(category -> modelMapper.map(category, CategoryResponseDto.class))
                .toList();
    }

    @Override
    @Cacheable(value = "getCategoryById",key = "#id")
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
    @CacheEvict(value = "getCategoryById",key = "#id")
    public boolean deleteCategory(Integer id) throws ResourceNotFoundException {
        Category category = categoryRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(()->new ResourceNotFoundException("Category Not Found!"));
        category.setIsDeleted(true);
            categoryRepository.save(category);
            cacheManagerService.removeCacheByName(Arrays.asList("allCategory","activeCategory"));
            return true;
    }
}
