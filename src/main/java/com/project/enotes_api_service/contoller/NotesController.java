package com.project.enotes_api_service.contoller;

import com.project.enotes_api_service.Endpoint.NotesEndPoint;
import com.project.enotes_api_service.dto.FavoriteNotesDto;
import com.project.enotes_api_service.dto.NotesDto;
import com.project.enotes_api_service.dto.NotesResponseDto;
import com.project.enotes_api_service.entity.FileDetails;
import com.project.enotes_api_service.service.NotesServiceImpl;
import com.project.enotes_api_service.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class NotesController implements NotesEndPoint {

    @Autowired
    private NotesServiceImpl notesService;

    public ResponseEntity<?> saveNotes(String notes,MultipartFile file) throws Exception{
        Boolean isSaved = notesService.savedNotes(notes,file);
        if(isSaved){
           return CommonUtil.createBuildResponseMessage("saved Success",HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Error Saving Notes",HttpStatus.INTERNAL_SERVER_ERROR);
    }


    public ResponseEntity<?> getAllNotes(){
        List<NotesDto> notesList = notesService.getAllNotes();
        if(CollectionUtils.isEmpty(notesList)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(notesList,HttpStatus.OK);
    }

    public ResponseEntity<?> downloadFile(Integer id) throws Exception {
        FileDetails fileDetails = notesService.getFileDetails(id);
        byte[] bytes = notesService.downloadFile(fileDetails);
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setContentType(CommonUtil.getContentType(fileDetails));
        httpHeaders.setContentDispositionFormData("attachment",fileDetails.getOriginalFileName());
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(bytes);
    }

    public ResponseEntity<?> getAllNotesByUser(
            Integer pageNo,
            Integer pageSize
            ) throws Exception{
        NotesResponseDto allNotesByUser = notesService.getAllNotesByUser(pageNo,pageSize);
        return CommonUtil.createBuildResponse(allNotesByUser,HttpStatus.OK);
    }

    public ResponseEntity<?> deleteNotes(Integer id) throws Exception{
        notesService.softDeleteNotes(id);
        return CommonUtil.createBuildResponseMessage("Delete Success",HttpStatus.OK);

    }

    @GetMapping("/restore/{id}")
    public ResponseEntity<?> restoreNotes(Integer id) throws Exception{
        notesService.restoreNotes(id);
        return CommonUtil.createBuildResponseMessage("Restore  Success",HttpStatus.OK);

    }

    public ResponseEntity<?> getUserRecycleBin() {
        List<NotesDto> userRecycleBin = notesService.getUserRecycleBin();
        if(CollectionUtils.isEmpty(userRecycleBin)){
            return  CommonUtil.createBuildResponse("Notes not available in Recycle Bin",HttpStatus.OK);
        }
        return CommonUtil.createBuildResponse(userRecycleBin,HttpStatus.OK);

    }

    public ResponseEntity<?> hardDeleteNotes(Integer id) throws Exception{
        notesService.hardDeleteNotes(id);
        return CommonUtil.createBuildResponseMessage(" HardDelete Success",HttpStatus.OK);
    }

    public ResponseEntity<?> emptyRecycleBin() throws Exception{
        notesService.emptyRecycleBin();
        return CommonUtil.createBuildResponseMessage(" Recycle Bin Empty",HttpStatus.OK);
    }


    public ResponseEntity<?> favoriteNotes(Integer notesId) throws Exception{
        notesService.favoriteNotes(notesId);
        return CommonUtil.createBuildResponseMessage("Noted Added to Favorite",HttpStatus.CREATED);
    }

    public ResponseEntity<?> unFavoriteNotes(Integer favoriteId) throws Exception{
        notesService.unfavoriteNotes(favoriteId);
        return CommonUtil.createBuildResponseMessage("Notes Removed From Favorite",HttpStatus.OK);
    }

    public ResponseEntity<?> getFavoriteNotes(){
        List<FavoriteNotesDto> userFavoriteNotes = notesService.getUserFavoriteNotes();
        if(CollectionUtils.isEmpty(userFavoriteNotes)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return CommonUtil.createBuildResponse(userFavoriteNotes,HttpStatus.OK);
    }


    public ResponseEntity<?> copyNotes(Integer notesId) throws Exception{
        Boolean isCopied = notesService.copyNotes(notesId);
        if(!isCopied){
            return CommonUtil.createErrorResponseMessage("Copy Failed Notes",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return CommonUtil.createBuildResponseMessage("Copied Success",HttpStatus.OK);
    }


    public ResponseEntity<?> searchNotes( String Keyword, Integer PageNo, Integer pageSize){
        NotesResponseDto notesResponseDto = notesService.getAllNotesByUserSearch(PageNo, pageSize, Keyword);
        if(!ObjectUtils.isEmpty(notesResponseDto)){
           return CommonUtil.createBuildResponse(notesResponseDto,HttpStatus.OK);
        }
        return CommonUtil.createErrorResponseMessage("No Notes Found",HttpStatus.NOT_FOUND);
    }

}
