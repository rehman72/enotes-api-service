package com.project.enotes_api_service.repository;

import com.project.enotes_api_service.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
