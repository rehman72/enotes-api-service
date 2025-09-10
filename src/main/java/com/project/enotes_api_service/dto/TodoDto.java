package com.project.enotes_api_service.dto;


import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {

    private Integer id;

    private String title;

    private StatusDto status;

    private Integer createdBy;

    private LocalDateTime createdOn;

    private Integer updatedBy;

    private LocalDateTime updatedOn;

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    @Builder
    public static   class StatusDto {

        private Integer id;

        private String name;

    }


}
