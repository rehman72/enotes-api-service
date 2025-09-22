package com.project.enotes_api_service.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserResponse {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String mobNo;

    private StatusDto accountStatus;

    private List<RoleDto> roles;

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    @Builder
    public static class StatusDto{

        private Integer id;

        private Boolean isActive;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    @Builder
    public static class RoleDto{
        private Integer id;
        private String name;
    }

}
