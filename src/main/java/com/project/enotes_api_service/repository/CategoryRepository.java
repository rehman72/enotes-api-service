package com.project.enotes_api_service.repository;

import com.project.enotes_api_service.Exception.ResourceNotFoundException;
import com.project.enotes_api_service.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByIdAndIsDeletedFalse(Integer id) throws Exception;

    @Query("select  c from Category as c where c.isDeleted=false")
    List<Category> findAllAndIsDeletedFalse();

    List<Category> findByIsActiveTrueAndIsDeletedFalse();
}
