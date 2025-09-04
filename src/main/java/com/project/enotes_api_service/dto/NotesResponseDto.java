package com.project.enotes_api_service.dto;


import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class NotesResponseDto {

    private List<NotesDto> notes;

    private Integer pageNo;

    private Integer pageSize;

    private Long totalElements;

    private Integer totalPages;

    private Boolean isFirst;

    private Boolean isLast;
}
