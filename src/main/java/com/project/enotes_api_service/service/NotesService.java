package com.project.enotes_api_service.service;

import com.project.enotes_api_service.dto.NotesDto;
import com.project.enotes_api_service.entity.FileDetails;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;

public interface NotesService {

    Boolean savedNotes(String notes, MultipartFile file) throws Exception;

    List<NotesDto> geAllNotes();

    byte[] downloadFile(FileDetails id) throws Exception;

    FileDetails getFileDetails(Integer id) throws Exception;
}
