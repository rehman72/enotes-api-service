package com.project.enotes_api_service.Endpoint;

import com.project.enotes_api_service.dto.TodoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static com.project.enotes_api_service.util.Constants.ROLE_USER;

@RequestMapping("/api/v1/todo")
public interface TodoEndPoint {
    @RequestMapping("/save-todo")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto);

    @RequestMapping("/{todoId}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getTodo(@PathVariable Integer todoId) throws Exception;

    @RequestMapping("/list")
    @PreAuthorize(ROLE_USER)
     ResponseEntity<?> getTodoByUser() throws
            Exception;
}
