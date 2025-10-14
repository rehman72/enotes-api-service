package com.project.enotes_api_service.Endpoint;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import static com.project.enotes_api_service.util.Constants.ROLE_ADMIN;
import static com.project.enotes_api_service.util.Constants.ROLE_USER;
import static com.project.enotes_api_service.util.Constants.ROLE_ADMIN_USER;
import static com.project.enotes_api_service.util.Constants.defaultPageNo;
import static com.project.enotes_api_service.util.Constants.defaultPageSize;
@Tag(name = "Notes Apis's",description = "All Notes Apis's")
@RequestMapping("/api/v1/notes")
public interface NotesEndPoint {

    @Operation(summary = "Save Notes",tags = {"Notes Apis's","User"})
    @PostMapping("/")
    @PreAuthorize(ROLE_USER)
     ResponseEntity<?> saveNotes(
            @RequestParam String notes
            ,@RequestParam(required = false) MultipartFile file
    ) throws Exception;

    @Operation(summary = "Get All Notes",tags = "Notes Apis's")
    @GetMapping("/")
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> getAllNotes();

    @Operation(summary = "Dowload File",tags = "Notes Apis's")
    @PostMapping("/download/{id}")
    @PreAuthorize(ROLE_ADMIN_USER)
    ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Get All Notes Logged In User Using pagination",tags = {"Notes Apis's","User"})
    @GetMapping("/user-notes")
    @PreAuthorize(ROLE_USER)
     ResponseEntity<?> getAllNotesByUser(
            @RequestParam(value = "pageNo",defaultValue = defaultPageNo) Integer pageNo,
            @RequestParam(value = "pageSize",defaultValue =defaultPageSize) Integer pageSize
    ) throws Exception;

    @Operation(summary = "Delete Single Note For User",tags = {"Notes Apis's","User"})
    @GetMapping("/delete/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Restore Deleted Notes",tags = {"Notes Apis's","User"})
    @GetMapping("/restore/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Get all Deleted Notes",tags = {"Notes Apis's","User"})
    @GetMapping("/recycle-bin")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getUserRecycleBin();

    @Operation(summary = "Delete Notes Permenantly",tags = {"Notes Apis's","User"})
    @DeleteMapping("/delete/{id}")
    @PreAuthorize(ROLE_USER)
     ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Clear Recycle Bin",tags = {"Notes Apis's","User"})
    @DeleteMapping("/empty-recycle-bin")
    @PreAuthorize(ROLE_USER)
     ResponseEntity<?> emptyRecycleBin() throws Exception;

    @Operation(summary = "Add Note to Favourite Notes",tags = {"Notes Apis's","User"})
    @GetMapping("/add-fav/{notesId}")
    @PreAuthorize(ROLE_USER)
     ResponseEntity<?> favoriteNotes(@PathVariable Integer notesId) throws Exception;

    @Operation(summary = "Remove From UnFavourite Notes",tags = {"Notes Apis's","User"})
    @GetMapping("un-fav-notes/{favoriteId}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> unFavoriteNotes(@PathVariable Integer favoriteId) throws Exception;

    @Operation(summary = "Get All favorite Notes For User",tags = {"Notes Apis's","User"})
    @GetMapping("/fav")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getFavoriteNotes();

    @Operation(summary = "Copy Notes",tags = {"Notes Apis's","User"})
    @GetMapping("/copy/{notesId}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> copyNotes(@PathVariable Integer notesId) throws Exception;

    @Operation(summary = "Search Note Which Keyword&Pagination",tags = {"Notes Apis's","User"})
    @GetMapping("/search")
    @PreAuthorize(ROLE_USER)
     ResponseEntity<?> searchNotes(@RequestParam(name = "key") String Keyword,
                                         @RequestParam(name = "PageNo",defaultValue =defaultPageNo) Integer PageNo,
                                         @RequestParam(name = "PageSize",defaultValue =defaultPageSize) Integer pageSize
    );
}
