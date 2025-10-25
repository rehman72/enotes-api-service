package com.project.enotes_api_service.service;

import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CacheManagerService  {

    Cache getCacheName(String cacheName);

    List<String> getAllCache();

    void removeAllCache();

    void removeCacheByName(List<String> cacheName);

}
