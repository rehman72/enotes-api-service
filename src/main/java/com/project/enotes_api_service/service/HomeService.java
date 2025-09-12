package com.project.enotes_api_service.service;

public interface HomeService {

    Boolean verifyAccount(Integer userId,String token) throws Exception;
}
