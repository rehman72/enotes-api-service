package com.project.enotes_api_service.dto;


import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {

    private Integer id;

    private String name;

    private String description;

}
