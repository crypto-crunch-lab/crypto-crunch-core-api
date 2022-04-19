package com.crypto.crunch.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class CryptoCrunchCoreApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoCrunchCoreApiApplication.class, args);
    }

}
