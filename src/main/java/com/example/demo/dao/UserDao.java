package com.example.demo.dao;

import com.example.demo.entity.User;

import java.util.List;

public interface UserDao {

    //創建用戶
    String CreateUser(User user);

    //根據用戶名稱username查詢數據
    List<User> ReadByUsername(String username);
    //根據用戶id查詢數據
    List<User> ReadByUid(Integer uid);
    //根據用戶uid修改數據
    String UpdateByUid(Integer uid,User user);


}
