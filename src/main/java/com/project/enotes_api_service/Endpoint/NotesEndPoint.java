package com.project.enotes_api_service.Endpoint;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import static com.project.enotes_api_service.util.Constants.ROLE_ADMIN;
import static com.project.enotes_api_service.util.Constants.ROLE_USER;
import static com.project.enotes_api_service.util.Constants.ROLE_ADMIN_USER;
import static com.project.enotes_api_service.util.Constants.defaultPageNo;
import static com.project.enotes_api_service.util.Constants.defaultPageSize;

@RequestMapping("/api/v1/notes")
public interface NotesEndPoint {

    @PostMapping("/")
    @PreAuthorize(ROLE_USER)
     ResponseEntity<?> saveNotes(
            @RequestParam String notes
            ,@RequestParam(required = false) MultipartFile file
    ) throws Exception;

    @GetMapping("/")
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> getAllNotes();

    @RequestMapping("/download/{id}")
    @PreAuthorize(ROLE_ADMIN_USER)
    ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;

    @GetMapping("/user-notes")
     ResponseEntity<?> getAllNotesByUser(
            @RequestParam(value = "pageNo",defaultValue = defaultPageNo) Integer pageNo,
            @RequestParam(value = "pageSize",defaultValue =defaultPageSize) Integer pageSize
    ) throws Exception;

    @GetMapping("/delete/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;

    @GetMapping("/restore/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;

    @GetMapping("/recycle-bin")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getUserRecycleBin();

    @DeleteMapping("/delete/{id}")
    @PreAuthorize(ROLE_USER)
     ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;

    @DeleteMapping("/empty-recycle-bin")
    @PreAuthorize(ROLE_USER)
     ResponseEntity<?> emptyRecycleBin() throws Exception;

    @GetMapping("/add-fav/{notesId}")
    @PreAuthorize(ROLE_USER)
     ResponseEntity<?> favoriteNotes(@PathVariable Integer notesId) throws Exception;

    @GetMapping("un-fav-notes/{favoriteId}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> unFavoriteNotes(@PathVariable Integer favoriteId) throws Exception;

    @GetMapping("/fav")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getFavoriteNotes();

    @GetMapping("/copy/{notesId}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> copyNotes(@PathVariable Integer notesId) throws Exception;

    @GetMapping("/search")
    @PreAuthorize(ROLE_USER)
     ResponseEntity<?> searchNotes(@RequestParam(name = "key") String Keyword,
                                         @RequestParam(name = "PageNo",defaultValue =defaultPageNo) Integer PageNo,
                                         @RequestParam(name = "PageSize",defaultValue =defaultPageSize) Integer pageSize
    );
}
