package com.project.enotes_api_service.mapper;

import com.project.enotes_api_service.dto.TodoDto;
import com.project.enotes_api_service.entity.Todo;

public class MapperTodo {

//   DTO to Entity
    public static Todo mapToEntity(TodoDto todoDto){
        Todo todo=new Todo();
        todo.setId(todoDto.getId());
        todo.setTitle(todoDto.getTitle());
        todo.setStatusId(todoDto.getStatus().getId());
        return todo;
    }
//    Entity to DTO
    public static TodoDto mapToDTO(Todo todo){
        TodoDto todoDto=new TodoDto();
        todoDto.setId(todo.getId());
        todoDto.setTitle(todo.getTitle());
        todoDto.setCreatedBy(todo.getCreatedBy());
        todoDto.setUpdatedBy(todo.getUpdatedBy());
        todoDto.setUpdatedOn(todo.getUpdatedOn());
        return todoDto;
    }


    public static TodoDto mapTodoDto(Todo todo){
        TodoDto todoDto=new TodoDto();
        todoDto.setId(todo.getId());
        todoDto.setTitle(todo.getTitle());
        todoDto.setCreatedBy(todo.getCreatedBy());
        todoDto.setUpdatedBy(todo.getUpdatedBy());
        todoDto.setUpdatedOn(todo.getUpdatedOn());
        return todoDto;
    }





}
