package com.zzc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class OpenApiInterfaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenApiInterfaceApplication.class, args);
    }

}
