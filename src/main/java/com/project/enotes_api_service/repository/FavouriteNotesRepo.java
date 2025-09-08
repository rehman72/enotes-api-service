package com.project.enotes_api_service.repository;

import com.project.enotes_api_service.entity.FavouriteNotes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface FavouriteNotesRepo extends JpaRepository<FavouriteNotes,Integer> {
    List<FavouriteNotes> findByUserId(Integer userId);

}
