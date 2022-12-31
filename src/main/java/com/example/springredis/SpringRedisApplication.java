package com.example.springredis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("com/example/springredis/mapper")
@EnableCaching
@EnableAsync
public class SpringRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRedisApplication.class, args);
    }

}
