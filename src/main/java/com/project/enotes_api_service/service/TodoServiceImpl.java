package com.project.enotes_api_service.service;

import com.project.enotes_api_service.Enums.TodoStatus;
import com.project.enotes_api_service.Exception.ResourceNotFoundException;
import com.project.enotes_api_service.dto.TodoDto;
import com.project.enotes_api_service.entity.Todo;
import com.project.enotes_api_service.entity.User;
import com.project.enotes_api_service.mapper.MapperTodo;
import com.project.enotes_api_service.repository.TodoRepository;
import com.project.enotes_api_service.util.CommonUtil;
import com.project.enotes_api_service.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public Boolean saveTodo(TodoDto todo) {
//        Validate Todo's Status
        Validation.todoValidation(todo);
        Todo todos = MapperTodo.mapToEntity(todo);
//        todos.setStatusId(todo.getStatus().getId());
        Todo savedTodo = todoRepository.save(todos);
        if(!ObjectUtils.isEmpty(savedTodo)){
            return true;
        }

        return false;
    }

    @Override
    public TodoDto getTodoById(Integer id) throws Exception{
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not Found with Id"));
        TodoDto Tododto = modelMapper.map(todo, TodoDto.class);
        setStatus(Tododto,todo);
        return Tododto;
    }

    private void setStatus(TodoDto tododto, Todo todo) {
        for(TodoStatus status:TodoStatus.values()){
            if(status.getId().equals(todo.getStatusId())){
                TodoDto.StatusDto statusDto = TodoDto.StatusDto.builder()
                        .id(status.getId())
                        .name(status.getName())
                        .build();
                tododto.setStatus(statusDto);
            }
        }
    }

    @Override
    public List<TodoDto> getTodoByUser() {
        User loggedInUser = CommonUtil.getLoggedInUser();
        List<Todo> allTodoWithUser = todoRepository.findByCreatedBy(loggedInUser.getId());
        return allTodoWithUser.stream()
                .map(todo -> modelMapper.map(todo, TodoDto.class))
                .toList();

    }
}
