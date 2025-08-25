package com.project.enotes_api_service.util;

import com.project.enotes_api_service.Exception.ValidationException;
import com.project.enotes_api_service.dto.CategoryDto;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class Validation {

    public void CategoryValidation(CategoryDto categoryDto){
        Map<String,Object> error=new LinkedHashMap<>();
        if(ObjectUtils.isEmpty(categoryDto)){
            throw new IllegalArgumentException("Pass Category object as {JSON} Body");
        }else{
            validateName(categoryDto,error);
            validateDescription(categoryDto,error);
        }
        if(!error.isEmpty()){
            throw new ValidationException(error);
        }
    }

    public void validateName(CategoryDto categoryDto,Map<String,Object> error){
        String name = categoryDto.getName();
        if(ObjectUtils.isEmpty(name)){
            error.put("name","Name Field Cannot be null");
        }else if (name.length()<3){
            error.put("name","Name Field Must be at least 3 characters");
        }
    }
    public void validateDescription(CategoryDto categoryDto,Map<String,Object> error){
        String description = categoryDto.getDescription();
        if(ObjectUtils.isEmpty(description)){
            error.put("description","Description Field Cannot be null");
        }else if(description.length()<10 || description.length()>25){
            error.put("description","Description Field Must be minimum  10 to 25 characters");
        }
    }

}


