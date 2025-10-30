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
    public Boolean saveCategory(CategoryDto categoryDto) throws ResourceNotFoundException {
        validation.CategoryValidation(categoryDto);
        Boolean isExists = categoryRepository.existsByName(categoryDto.getName());
        if(isExists){
            throw new  AlreadyExistException("Category Already Exists");
        }
        Category category = modelMapper.map(categoryDto, Category.class);
        if(category.getId() == null){
            newCategory(category);
        }else{
            existingCategory(category.getId());
        }
        categoryRepository.save(category);
        return true;
    }

    public void existingCategory(Integer categoryId) throws ResourceNotFoundException {
        categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category Not Found!"));
    }

    public void newCategory(Category category) {
        category.setIsDeleted(false);
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
        Category category=categoryRepository.findByIdAndIsDeletedFalse(id)
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
