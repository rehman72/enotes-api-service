package com.project.enotes_api_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Category  extends  BaseModel{

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private  Integer id;

    private  String name;

    private String description;

    private  Boolean isActive;

    private  Boolean isDeleted;
}
