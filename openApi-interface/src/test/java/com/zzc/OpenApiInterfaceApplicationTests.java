package com.zzc;

import com.zzc.client.UserClient;
import com.zzc.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OpenApiInterfaceApplicationTests {

    @Autowired
    private UserClient userClient;

    @Test
    void contextLoads() {
        User user = new User();
        user.setUserName("gy");
        System.out.println(userClient.getUserByBody(user));
    }

}
