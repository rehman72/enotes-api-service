package com.project.enotes_api_service.Handler;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
        Map<String,Object> map=new ConcurrentHashMap<>();
        map.put("status",responseStatus); //success/fail
        map.put("message",message); //saved Success
        if(!ObjectUtils.isEmpty(data)){ //data body
            map.put("data",data);
        }
        return new ResponseEntity<>(map,responseStatus);

    }
}
