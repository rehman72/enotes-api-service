package com.project.enotes_api_service.service;
import com.project.enotes_api_service.dto.TodoDto;
import java.util.List;

public interface TodoService {

    Boolean saveTodo(TodoDto todo);

    TodoDto getTodoById(Integer id) throws Exception;

    List<TodoDto> getTodoByUser() throws Exception;

}
