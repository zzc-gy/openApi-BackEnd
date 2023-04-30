package com.zzc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.zzc.mapper")
@SpringBootApplication
public class OpenApiBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenApiBackEndApplication.class, args);
    }

}
