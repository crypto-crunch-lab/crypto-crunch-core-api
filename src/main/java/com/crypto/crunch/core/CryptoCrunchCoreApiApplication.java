package com.crypto.crunch.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class CryptoCrunchCoreApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoCrunchCoreApiApplication.class, args);
    }

}
