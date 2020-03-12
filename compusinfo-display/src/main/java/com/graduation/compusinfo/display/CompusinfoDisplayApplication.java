package com.graduation.compusinfo.display;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan("com.graduation.compusinfo.display.mapper")
@EnableSwagger2
@SpringBootApplication
@EnableCaching
public class CompusinfoDisplayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompusinfoDisplayApplication.class, args);
    }

}
