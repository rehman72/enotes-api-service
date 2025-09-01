package com.project.enotes_api_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotesDto {

    private Integer id;

    private String title;

    private String description;

    private CategoryDto category;

    private Integer createBy;

    private LocalDateTime createdOn;

    private Integer updatedBy;

    private LocalDateTime updatedOn;

    private FileDetailsDto fileDetails;

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static  class CategoryDto{
        private Integer id;
        private String name;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class FileDetailsDto{
        private Integer id;

        private String originalFileName;

        private String uploadFileName;
    }

}
