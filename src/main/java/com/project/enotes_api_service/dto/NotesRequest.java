package com.project.enotes_api_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.project.enotes_api_service.dto.NotesDto.CategoryDto;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NotesRequest {

    private String title;

    private String description;

    private CategoryDto Category;


}
