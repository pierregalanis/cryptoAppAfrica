package com.africacrypto.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DummyCacheService {

    @Cacheable(value="testCache", key="#name")
    public String greet( String name){
        System.out.println("❌ Not from cache");
        return "Hello " + name + "at " + LocalDateTime.now();
    }
}
