package com.example.demo.service;


import com.example.demo.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {

    //創建用戶
    String CreateUser(User user);
    //根據用戶名稱username查詢數據
    List<User> ReadByUsername(String username);

    List<User> login(String username, String password);

    String UpdateByUid(Integer uid,User user);
}
