package com.project.enotes_api_service.entity;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class BaseModel {

    private  Boolean isActive;

    private  Boolean isDeleted;

    private Integer createBy;

    private LocalDateTime createdOn;
    
    private Integer updatedBy;

    private LocalDateTime updatedOn;
}
