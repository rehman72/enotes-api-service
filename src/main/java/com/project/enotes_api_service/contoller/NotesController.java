package com.project.enotes_api_service.contoller;

import com.project.enotes_api_service.dto.NotesDto;
import com.project.enotes_api_service.service.NotesServiceImpl;
import com.project.enotes_api_service.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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



}
