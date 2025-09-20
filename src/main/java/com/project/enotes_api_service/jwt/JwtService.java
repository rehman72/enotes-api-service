package com.project.enotes_api_service.jwt;

import com.project.enotes_api_service.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generateToken(User user);

    String extractUsername(String token);

    Boolean validateToken(String token,UserDetails userDetails) ;


}
