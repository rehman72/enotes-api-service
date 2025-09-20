package com.project.enotes_api_service.jwt;

import com.project.enotes_api_service.Exception.JwtTokenExpiredException;
import com.project.enotes_api_service.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    private  final SecretKey  secretKey=Keys.hmacShaKeyFor("ijfhshehuehkjnzkjczheewixkuu4hdfjjfjbjbfhrbjhb".getBytes());


    @Override
    public String generateToken(User user) {

        Map<String,Object>
                map = new HashMap<>();
        map.put("Id",user.getId());
        map.put("user_roles",user.getRoles().stream()
                .map(role->"ROLE_" + role.getName())
                .toList());
        map.put("Status",user.getAccountStatus().getIsActive());

        String token = Jwts
                .builder()
                .claims().add(map)
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1)))
                .and()
                .signWith(secretKey,SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    @Override
    public String extractUsername(String token) {
       Claims claims=extractAllClaims(token);
       return claims.getSubject();
    }

        public Claims extractAllClaims(String token){
            Claims payload=null;
            try {
            payload= Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }catch (ExpiredJwtException e){
            return e.getClaims();
        }
            return payload;
        }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails){
        if(tokenExpired(token)){
            throw new JwtTokenExpiredException("Token Expired");
        }
        String username = extractUsername(token);
        if(!username.equals(userDetails.getUsername())){
            log.info("ValidateToken:: username not match");
            return false;
        }
        List<String> userRoles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        List<String> tokenRole=getRole(token);
        if(!tokenRole.containsAll(userRoles)){
            throw new SecurityException("Token does not match User Role!");
        }
        log.info("Role : {}",getRole(token));
        log.info("ValidateToken:: token :: {}",token);
        log.info("Subject Claims: {}",extractUsername(token));
        log.info("Payload: {}",extractAllClaims(token).toString());
       log.info("UserId : {}",getUserId(token));
       return true;
    }

    public List<String> getRole(String token){
        Claims claims = extractAllClaims(token);
        List<String> userRoles = claims.get("user_roles", List.class);
        return userRoles;
    }

    public Integer getUserId(String token){
        Claims claims = extractAllClaims(token);
        Integer userId = claims.get("Id", Integer.class);
        return userId;
    }

    public Date getExpiredTime(String token){
        Claims claims = extractAllClaims(token);
       return claims.getExpiration();
    }

    public Boolean tokenExpired(String token){
        Claims claims = extractAllClaims(token);
        Date expiration = claims.getExpiration();
       return expiration.before(new Date());
    }


}
