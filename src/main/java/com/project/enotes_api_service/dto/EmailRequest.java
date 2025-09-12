package com.project.enotes_api_service.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class EmailRequest {

    private String to;

    private String Subject;

    private String title;

    private String message;
}
