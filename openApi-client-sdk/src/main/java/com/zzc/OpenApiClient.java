package com.zzc;

import com.zzc.client.UserClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author zzc
 * @Date 2023/4/30 21:29
 */
@ComponentScan
@Data
@Configuration
@ConfigurationProperties("open-api.client")
public class OpenApiClient {

    private String accessKey;

    private String secretKey;

    @Bean
    public UserClient openApiClient() {
        return new UserClient(accessKey, secretKey);
    }

}
