package com.zzc.controller;

import com.zzc.model.entity.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * UserController
 *
 * @Author zzc
 * @Date 2023/4/30 19:42
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/get")
    public User getUserByGet(String name, HttpServletRequest request){
        String header = request.getHeader("source");
        User user = new User();
        user.setUserName(name);
        return user;
    }

    @PostMapping("/post")
    public User getUserByPost(String name){
        User user = new User();
        user.setUserName(name);
        return user;
    }

    @PostMapping("/userBody")
    public User getUserByBody(@RequestBody User user, HttpServletRequest request){
        return user;
    }

}
