package com.zzc;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.zzc.mapper")
@SpringBootApplication
@EnableDubbo
public class OpenApiBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenApiBackEndApplication.class, args);
    }

}
