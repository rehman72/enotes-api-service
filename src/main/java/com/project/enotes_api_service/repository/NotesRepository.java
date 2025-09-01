package com.project.enotes_api_service.repository;

import com.project.enotes_api_service.entity.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Notes, Integer> {
}
