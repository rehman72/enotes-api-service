package com.project.enotes_api_service.service;

import com.project.enotes_api_service.dto.NotesDto;
import com.project.enotes_api_service.dto.NotesResponseDto;
import com.project.enotes_api_service.entity.FileDetails;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface NotesService {

    Boolean savedNotes(String notes, MultipartFile file) throws Exception;

    List<NotesDto> geAllNotes();

    byte[] downloadFile(FileDetails id) throws Exception;

    FileDetails getFileDetails(Integer id) throws Exception;

    NotesResponseDto getAllNotesByUser(Integer userId,Integer pageNo,Integer pageSize) throws Exception;

    void softDeleteNotes(Integer id) throws Exception;

    void restoreNotes(Integer id) throws Exception;

    List<NotesDto> getUserRecycleBin(Integer userId);
}

