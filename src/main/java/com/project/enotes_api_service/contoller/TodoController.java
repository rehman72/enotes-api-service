package com.project.enotes_api_service.contoller;

import com.project.enotes_api_service.Endpoint.TodoEndPoint;
import com.project.enotes_api_service.dto.TodoDto;
import com.project.enotes_api_service.service.TodoService;
import com.project.enotes_api_service.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TodoController implements TodoEndPoint {

    @Autowired
    private TodoService todoService;

    public ResponseEntity<?> saveTodo(TodoDto todoDto) {
        Boolean isSaved = todoService.saveTodo(todoDto);
        if (isSaved) {
            return CommonUtil.createBuildResponseMessage("Todo Created", HttpStatus.CREATED);
        } else {
            return CommonUtil.createErrorResponseMessage("Todo Not Saved!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getTodo(Integer todoId) throws Exception {
        TodoDto todo = todoService.getTodoById(todoId);
        if (!ObjectUtils.isEmpty(todo)) {
            return CommonUtil.createBuildResponse(todo, HttpStatus.OK);
        } else {
            return CommonUtil.createErrorResponseMessage("Todo Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getTodoByUser() throws
            Exception{
        List<TodoDto> todoList = todoService.getTodoByUser();
        if (!ObjectUtils.isEmpty(todoList)) {
            return CommonUtil.createBuildResponse(todoList, HttpStatus.OK);
        } else {
            return CommonUtil.createBuildResponse(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
