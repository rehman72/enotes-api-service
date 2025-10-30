package com.project.enotes_api_service;


import com.project.enotes_api_service.dto.CategoryDto;
import com.project.enotes_api_service.entity.Category;
import com.project.enotes_api_service.repository.CategoryRepository;
import com.project.enotes_api_service.service.CategoryServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@EnableCaching
@Slf4j
class EnotesApiServiceApplicationTests {

    @MockitoBean
    private CategoryRepository categoryRepository;

    @MockitoBean
    private ModelMapper modelMapper;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private CacheManager cacheManager;

    @Mock
    public MockServletContext servletContext;

    @BeforeEach
     void clearCache() {
         Cache cache = cacheManager.getCache("allCategories");
         if(cache !=null) cache.clear();
     }

     @Test
     public  void testGetAllCategory_Cacheable(){
         Category category1 = new Category(1, "Books", "All kinds of books");
         Category category2 = new Category(2, "Movies", "All kinds of movies");

         when(categoryRepository.findByIsDeletedFalse())
                .thenReturn(List.of(category1,category2));
         when(modelMapper.map(category1,CategoryDto.class)).thenReturn(
                 CategoryDto.builder()
                         .id(category1.getId())
                         .name(category1.getName())
                         .description(category1.getDescription())
                         .build());

         when(modelMapper.map(category2,CategoryDto.class)).thenReturn(
                 CategoryDto.builder()
                         .id(category2.getId())
                         .name(category2.getName())
                         .description(category2.getDescription())
                         .build());

//        Act
         List<CategoryDto> result = categoryService.getAllCategory();

         assertNotNull(result);
         assertEquals(2, result.size());
         assertEquals(category1.getName(),result.get(0).getName());
         assertEquals(category2.getName(),result.get(1).getName());
         verify(categoryRepository,times(1)).findByIsDeletedFalse();

     }


}
