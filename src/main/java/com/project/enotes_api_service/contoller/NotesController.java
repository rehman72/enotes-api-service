package com.project.enotes_api_service.contoller;

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
        List<NotesDto> notesList = notesService.geAllNotes();
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


}
