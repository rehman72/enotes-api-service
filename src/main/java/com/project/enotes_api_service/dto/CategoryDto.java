package com.project.enotes_api_service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Integer id;

    private String name;

    private String description;

    private Boolean isActive;

    private Integer createBy;

    private LocalDateTime createdOn;

    private Integer updatedBy;

    private LocalDateTime updatedOn;


}
