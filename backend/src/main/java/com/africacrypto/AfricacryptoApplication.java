package com.africacrypto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.africacrypto")
public class AfricacryptoApplication {

    public static void main(String[] args) {

        SpringApplication.run(AfricacryptoApplication.class, args);
    }

}
