package com.project.enotes_api_service.service;

import com.project.enotes_api_service.entity.Category;
import com.project.enotes_api_service.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public Boolean saveCategory(Category category) {
        category.setIsDeleted(false);
        category.setCreatedOn(LocalDateTime.now());
        category.setCreateBy(1);
       Category savedCategory= categoryRepository.save(category);
       if(ObjectUtils.isEmpty(savedCategory)){
          return false;
       }
       return true;
    }

    @Override
    public List<Category> getAllCategory() {
        return  categoryRepository
                .findAll();
    }
}
