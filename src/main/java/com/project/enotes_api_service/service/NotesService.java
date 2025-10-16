package com.project.enotes_api_service.service;

import com.project.enotes_api_service.dto.FavoriteNotesDto;
import com.project.enotes_api_service.dto.NotesDto;
import com.project.enotes_api_service.dto.NotesResponseDto;
import com.project.enotes_api_service.entity.FileDetails;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface NotesService {

    Boolean savedNotes(String notes, MultipartFile file) throws Exception;

    List<NotesDto> getAllNotes();

    byte[] downloadFile(FileDetails fileDetails) throws Exception;

    FileDetails getFileDetails(Integer id) throws Exception;

    NotesResponseDto getAllNotesByUser(Integer pageNo,Integer pageSize) throws Exception;

    NotesResponseDto getAllNotesByUserSearch(Integer pageNo,Integer pageSize,String keyword);

    void softDeleteNotes(Integer id) throws Exception;

    void restoreNotes(Integer id) throws Exception;

    List<NotesDto> getUserRecycleBin();

    void hardDeleteNotes(Integer id) throws Exception;

    void emptyRecycleBin() throws Exception;

    void favoriteNotes(Integer notesId) throws Exception;

    void unfavoriteNotes(Integer notesId) throws Exception;

    List<FavoriteNotesDto> getUserFavoriteNotes() throws Exception;

    Boolean copyNotes(Integer notesId) throws Exception;
}

