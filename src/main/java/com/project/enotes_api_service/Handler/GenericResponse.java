package com.project.enotes_api_service.Handler;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse {

    private HttpStatus  responseStatus;

    private String status;

    private String message;


    private Object data;

    public ResponseEntity<?> create(){
        Map<String,Object> map=new LinkedHashMap<>();
        map.put("message",message);//saved Success
        if(!ObjectUtils.isEmpty(status)){
            map.put("status",status);
        }
        map.put("httpStatus",responseStatus); //success/fail
        if(!ObjectUtils.isEmpty(data)){ //data body
            map.put("data",data);
        }
        return new ResponseEntity<>(map,responseStatus);

    }
}
