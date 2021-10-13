package com.example.demo.controller;


import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    //用戶註冊
    @PostMapping("/register")
    public String create(@RequestBody User user){
        return userService.CreateUser(user);
    }

    //用戶登入
    @PostMapping("/userlogin")
    public List<User> read(@RequestBody User user){
        return (userService.login(user.getUsername(),user.getPassword()));
    }

    @PutMapping("users/{uid}") //根據帳號做修改
    public String update(@PathVariable Integer uid,@RequestBody User user){ //@Path用來取得url路徑的值

        return userService.UpdateByUid(uid,user);
    }

}
