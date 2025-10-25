package com.project.enotes_api_service.service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class CacheManagerImpl implements CacheManagerService{

    private final CacheManager cacheManager;

    public CacheManagerImpl(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }


    @Override
    public List<String> getAllCache() {
        Collection<String> allCache =
                cacheManager.getCacheNames();
        return allCache.stream()
                .toList();
    }

    @Override
    public Cache getCacheName(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        log.info("Cache Name: {}",cache);
        return cache;
    }

    @Override
    public void removeAllCache() {
        cacheManager.getCacheNames()
                .forEach(cache-> {
                            log.info("Cache Deleted Name : {}",cache);
                            Objects.requireNonNull(cacheManager.getCache(cache)).clear();
                }
                );
    }

    @Override
    public void removeCacheByName(List<String> cacheName) {
        for(String cachenames:cacheName){
            Cache cache = cacheManager.getCache(cachenames);
            if(!ObjectUtils.isEmpty(cache)){
                cache.clear();
                log.info("Removed Cache Name : {}",cachenames);
            }else{
                log.info("Cache Names : {}",cacheManager.getCacheNames());
            }
        }}


}
