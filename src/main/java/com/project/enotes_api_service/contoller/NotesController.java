package com.project.enotes_api_service.contoller;

import com.project.enotes_api_service.dto.FavoriteNotesDto;
import com.project.enotes_api_service.dto.NotesDto;
import com.project.enotes_api_service.dto.NotesResponseDto;
import com.project.enotes_api_service.entity.FileDetails;
import com.project.enotes_api_service.service.NotesServiceImpl;
import com.project.enotes_api_service.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

    @Autowired
    private NotesServiceImpl notesService;

    @PostMapping("/")
    @PreAuthorize("hasAuthority('ROLE_USER')")
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllNotes(){
        List<NotesDto> notesList = notesService.getAllNotes();
        if(CollectionUtils.isEmpty(notesList)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(notesList,HttpStatus.OK);
    }

    @RequestMapping("/download/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
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
            @RequestParam(value = "pageSize",defaultValue = "1") Integer pageSize
            ) throws Exception{
        NotesResponseDto allNotesByUser = notesService.getAllNotesByUser(pageNo,pageSize);
        return CommonUtil.createBuildResponse(allNotesByUser,HttpStatus.OK);
    }


    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception{
        notesService.softDeleteNotes(id);
        return CommonUtil.createBuildResponseMessage("Delete Success",HttpStatus.OK);

    }

    @GetMapping("/restore/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception{
        notesService.restoreNotes(id);
        return CommonUtil.createBuildResponseMessage("Restore  Success",HttpStatus.OK);

    }

    @GetMapping("/recycle-bin")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> getUserRecycleBin() {
        List<NotesDto> userRecycleBin = notesService.getUserRecycleBin();
        if(CollectionUtils.isEmpty(userRecycleBin)){
            return  CommonUtil.createBuildResponse("Notes not available in Recycle Bin",HttpStatus.OK);
        }
        return CommonUtil.createBuildResponse(userRecycleBin,HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception{
        notesService.hardDeleteNotes(id);
        return CommonUtil.createBuildResponseMessage(" HardDelete Success",HttpStatus.OK);
    }

    @DeleteMapping("/empty-recycle-bin")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> emptyRecycleBin() throws Exception{
        notesService.emptyRecycleBin();
        return CommonUtil.createBuildResponseMessage(" Recycle Bin Empty",HttpStatus.OK);
    }

    @GetMapping("/add-fav/{notesId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> favoriteNotes(@PathVariable Integer notesId) throws Exception{
        notesService.favoriteNotes(notesId);
        return CommonUtil.createBuildResponseMessage("Noted Added to Favorite",HttpStatus.CREATED);
    }
    @GetMapping("un-fav-notes/{favoriteId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> unFavoriteNotes(@PathVariable Integer favoriteId) throws Exception{
        notesService.unfavoriteNotes(favoriteId);
        return CommonUtil.createBuildResponseMessage("Notes Removed From Favorite",HttpStatus.OK);
    }

    @GetMapping("/fav")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> getFavoriteNotes(){
        List<FavoriteNotesDto> userFavoriteNotes = notesService.getUserFavoriteNotes();
        if(CollectionUtils.isEmpty(userFavoriteNotes)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return CommonUtil.createBuildResponse(userFavoriteNotes,HttpStatus.OK);
    }

    @GetMapping("/copy/{notesId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> copyNotes(@PathVariable Integer notesId) throws Exception{
        Boolean isCopied = notesService.copyNotes(notesId);
        if(!isCopied){
            return CommonUtil.createErrorResponseMessage("Copy Failed Notes",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return CommonUtil.createBuildResponseMessage("Copied Success",HttpStatus.OK);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> searchNotes(@RequestParam(name = "key") String Keyword,
                                         @RequestParam(name = "PageNo",defaultValue = "0") Integer PageNo,
                                         @RequestParam(name = "PageSize",defaultValue = "1") Integer pageSize
                                         ){
        NotesResponseDto notesResponseDto = notesService.getAllNotesByUserSearch(PageNo, pageSize, Keyword);
        if(!ObjectUtils.isEmpty(notesResponseDto)){
           return CommonUtil.createBuildResponse(notesResponseDto,HttpStatus.OK);
        }
        return CommonUtil.createErrorResponseMessage("No Notes Found",HttpStatus.NOT_FOUND);
    }

}
