package com.example.demo.service;


import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.example.demo.utils.MD5util;
import com.example.demo.utils.UUIDutil;
import com.mysql.cj.exceptions.PasswordExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String CreateUser(User user) {
        //先檢查是否有重複名稱
        String username =user.getUsername();
        //如果查詢結果不為null，代表有重複名稱
        if(userDao.ReadByUsername(username) != null){
            //  拋出錯誤
            throw new IllegalArgumentException("用戶名稱重複");
        }
        //如果可以執行到這一行，代表用戶名稱沒有被使用
        //存取當下時間
        Date now =new Date();

        //密碼加密
        String salt = UUIDutil.uuid();
        user.setSalt(salt);
        String md5Password = MD5util.md5(user.getPassword(),salt);
        user.setPassword(md5Password);
        //開始設置其他後台參數，時間，使用者等等
        user.setIsDelete(0);
        user.setCreated_user(username);
        user.setCreated_time(now);
        user.setModified_user(username);
        user.setModified_time(now);

        return userDao.CreateUser(user);
    }

    @Override
    public List<User> login(String username, String password) {

        //新增陣列，來接受userDao.ReadByUsername的回傳資料
        List<User> list=new ArrayList<>();
        list=userDao.ReadByUsername(username);
        //因為只會有一筆，所以在陣列的第0個位置
        User user= list.get(0);

        if(userDao.ReadByUsername(username)== null){
            //  拋出錯誤
            throw new IllegalArgumentException("登入失敗，找不到帳號");
        }

        if(user.getIsDelete()==1){
            throw new IllegalArgumentException("登入失敗，帳號已經被刪除");
        }

        String salt = user.getSalt();
        String md5Password = MD5util.md5(password,salt);
        if(!user.getPassword().equals(md5Password)){
            throw new PasswordExpiredException("密碼輸入錯誤，請重新輸入");
        }

        return list;
    }

    @Override
    public List<User> ReadByUsername(String username) {
        return userDao.ReadByUsername(username);
    }

    @Override
    public String UpdateByUid(Integer uid, User user) {

        if(userDao.ReadByUid(uid)== null){
            //  拋出錯誤
            throw new IllegalArgumentException("獲取數據失敗，請再重新嘗試一次");
        }
        //新增陣列，來接受userDao.ReadByUsername的回傳資料
        List<User> list=new ArrayList<>();
        list=userDao.ReadByUid(uid);
        //因為只會有一筆，所以在陣列的第0個位置
        User olduser= list.get(0);

        if(olduser.getIsDelete()==1){
            throw new IllegalArgumentException("登入失敗，帳號已經被刪除");
        }

        olduser.setEmail(user.getEmail());
        olduser.setPhone(user.getPhone());
        olduser.setGender(user.getGender());
        olduser.setModified_time(new Date());
        olduser.setModified_user(user.getModified_user());

        return userDao.UpdateByUid(uid,olduser);
    }
}
