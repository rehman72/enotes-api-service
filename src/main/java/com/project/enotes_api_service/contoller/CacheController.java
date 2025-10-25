package com.project.enotes_api_service.contoller;


import com.project.enotes_api_service.Endpoint.CacheEndPoint;
import com.project.enotes_api_service.service.CacheManagerService;
import com.project.enotes_api_service.util.CommonUtil;
import org.springframework.cache.Cache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CacheController implements CacheEndPoint {

    private  final CacheManagerService cacheManagerService;

    public CacheController(CacheManagerService cacheManagerService) {
        this.cacheManagerService = cacheManagerService;
    }

    @Override
    public ResponseEntity<?> getCallCache() {
        List<String> NameCache=cacheManagerService.getAllCache();
        if(NameCache!=null){
            return ResponseEntity.ok(NameCache);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<?> getCache(String cacheName) {
        Cache cache = cacheManagerService.getCacheName(cacheName);
        if(ObjectUtils.isEmpty(cache)){
            return CommonUtil.createErrorResponseMessage("Cache Not Found!", HttpStatus.NOT_FOUND);
        }
        return CommonUtil.createBuildResponse(cache, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> clearAllCache() {
       cacheManagerService.removeAllCache();
       return ResponseEntity.ok().body("Cache cleared Successfully");
    }

}
