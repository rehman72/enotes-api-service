package com.project.enotes_api_service.repository;

import com.project.enotes_api_service.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo,Integer> {
    List<Todo> findByCreatedBy(Integer userId);
}
