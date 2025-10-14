package com.project.enotes_api_service.Endpoint;

import com.project.enotes_api_service.dto.TodoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.project.enotes_api_service.util.Constants.ROLE_USER;

@Tag(name = "Todo Apis's",description = "All Todo Apis's ")
@RequestMapping("/api/v1/todo")
public interface TodoEndPoint {
    @Operation(summary = "Save Todo",tags={"Todo Apis's","User"})
    @PostMapping("/save-todo")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto);

    @Operation(summary = "Get Todo for Particular User",tags={"Todo Apis's","User"})
    @GetMapping("/{todoId}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getTodo(@PathVariable Integer todoId) throws Exception;

    @Operation(summary = "List all Todo for Logged In User",tags={"Todo Apis's","User"})
    @GetMapping("/list")
    @PreAuthorize(ROLE_USER)
     ResponseEntity<?> getTodoByUser() throws
            Exception;
}
