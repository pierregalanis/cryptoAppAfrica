package com.africacrypto.controller;

import com.africacrypto.service.DummyCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestCacheController {



    private final DummyCacheService  dummyCacheService;

   @GetMapping("/greeting")
   public String greet(@RequestParam String name){
      return dummyCacheService.greet(name);
   }
}


