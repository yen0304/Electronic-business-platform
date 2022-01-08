package com.example.demo.controller;


import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;


    //用戶註冊
    @PostMapping("/register")
    public String create(@RequestBody User user){
        return userService.CreateUser(user);
    }

    //用戶登入
    //@cookieValue:0
    @PostMapping("/userlogin")
    public List<User> userlogin(HttpSession session, @RequestBody User user){
        List<User> list =new ArrayList<>();
        list =userService.login(user.getUsername(),user.getPassword());
        session.setAttribute("uid",list.get(0).getUid());
        session.setAttribute("username",list.get(0).getUsername());

        return (list);
    }

    @PutMapping("users/{uid}") //根據帳號做修改
    public String update(@PathVariable Integer uid,@RequestBody User user){ //@Path用來取得url路徑的值

        return userService.UpdateByUid(uid,user);
    }

    @GetMapping("users/{username}")
    public List<User> read(@PathVariable String username){ //@Path用來取得url路徑的值

        return userService.ReadByUsername(username);
    }




    @GetMapping("users/session-username")
    public  String getsessionusername(HttpSession session){ //@Path用來取得url路徑的值
        return (String)session.getAttribute("username");
    }

    //用戶登出
    @GetMapping("/sign_out")
    public String signout(HttpSession session){
        session.removeAttribute("uid");
        session.removeAttribute("username");
        return "登出成功";
    }

}
