package com.project.enotes_api_service.service;

import com.project.enotes_api_service.dto.NotesDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NotesService {

    Boolean savedNotes(String notes, MultipartFile file) throws Exception;

    List<NotesDto> geAllNotes();
}
