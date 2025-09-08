package com.project.enotes_api_service.contoller;

import com.project.enotes_api_service.dto.FavoriteNotesDto;
import com.project.enotes_api_service.dto.NotesDto;
import com.project.enotes_api_service.dto.NotesResponseDto;
import com.project.enotes_api_service.entity.FileDetails;
import com.project.enotes_api_service.service.NotesServiceImpl;
import com.project.enotes_api_service.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

    @Autowired
    private NotesServiceImpl notesService;

    @PostMapping("/")
    public ResponseEntity<?> saveNotes(
            @RequestParam String notes
             ,@RequestParam(required = false) MultipartFile file
    ) throws Exception{
        Boolean isSaved = notesService.savedNotes(notes,file);
        if(isSaved){
           return CommonUtil.createBuildResponseMessage("saved Success",HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Error Saving Notes",HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/")
    public ResponseEntity<?> getAllNotes(){
        List<NotesDto> notesList = notesService.getAllNotes();
        if(CollectionUtils.isEmpty(notesList)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(notesList,HttpStatus.OK);
    }

    @RequestMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception {
        FileDetails fileDetails = notesService.getFileDetails(id);
        byte[] bytes = notesService.downloadFile(fileDetails);
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setContentType(CommonUtil.getContentType(fileDetails));
        httpHeaders.setContentDispositionFormData("attachment",fileDetails.getOriginalFileName());
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(bytes);
    }

    @GetMapping("/user-notes")
    public ResponseEntity<?> getAllNotesByUser(
            @RequestParam(value = "pageNo",defaultValue = "0") Integer pageNo,
            @RequestParam(value = "PageSize",defaultValue = "1") Integer pageSize
            ) throws Exception{
      Integer userId=1;
        NotesResponseDto allNotesByUser = notesService.getAllNotesByUser(userId,pageNo,pageSize);
        return CommonUtil.createBuildResponse(allNotesByUser,HttpStatus.OK);
    }


    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception{
        notesService.softDeleteNotes(id);
        return CommonUtil.createBuildResponseMessage("Delete Success",HttpStatus.OK);

    }

    @GetMapping("/restore/{id}")
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception{
        notesService.restoreNotes(id);
        return CommonUtil.createBuildResponseMessage("Restore  Success",HttpStatus.OK);

    }

    @GetMapping("/recycle-bin")
    public ResponseEntity<?> getUserRecycleBin() {
        Integer userId=1;
        List<NotesDto> userRecycleBin = notesService.getUserRecycleBin(userId);
        if(CollectionUtils.isEmpty(userRecycleBin)){
            return  CommonUtil.createBuildResponse("Notes not available in Recycle Bin",HttpStatus.OK);
        }
        return CommonUtil.createBuildResponse(userRecycleBin,HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception{
        notesService.hardDeleteNotes(id);
        return CommonUtil.createBuildResponseMessage(" HardDelete Success",HttpStatus.OK);
    }

    @DeleteMapping("/empty-recycle-bin")
    public ResponseEntity<?> emptyRecycleBin() throws Exception{
        Integer userId=1;
        notesService.emptyRecycleBin(userId);
        return CommonUtil.createBuildResponseMessage(" Recycle Bin Empty",HttpStatus.OK);
    }

    @GetMapping("/add-fav/{notesId}")
    public ResponseEntity<?> favoriteNotes(@PathVariable Integer notesId) throws Exception{
        notesService.favoriteNotes(notesId);
        return CommonUtil.createBuildResponseMessage("Noted Added to Favorite",HttpStatus.CREATED);
    }
    @GetMapping("un-fav-notes/{favoriteId}")
    public ResponseEntity<?> unFavoriteNotes(@PathVariable Integer favoriteId) throws Exception{
        notesService.unfavoriteNotes(favoriteId);
        return CommonUtil.createBuildResponseMessage("Notes Removed From Favorite",HttpStatus.OK);
    }

    @GetMapping("/fav")
    public ResponseEntity<?> getFavoriteNotes(){
        List<FavoriteNotesDto> userFavoriteNotes = notesService.getUserFavoriteNotes();
        if(CollectionUtils.isEmpty(userFavoriteNotes)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return CommonUtil.createBuildResponse(userFavoriteNotes,HttpStatus.OK);
    }

}
