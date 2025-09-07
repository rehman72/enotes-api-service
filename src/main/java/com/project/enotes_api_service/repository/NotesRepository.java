package com.project.enotes_api_service.repository;

import com.project.enotes_api_service.entity.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NotesRepository extends JpaRepository<Notes, Integer> {
    Page<Notes> findByCreatedByAndIsDeletedFalse(Integer createBy, Pageable pageable);

    List<Notes> findByCreatedByAndIsDeletedTrue(Integer userId);

    List<Notes> findAllByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime localDateTime);

    List<Notes> findAllByCreatedByAndIsDeletedTrue(Integer createdBy);
}
