package com.example.demo.service;


import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import com.mysql.cj.exceptions.PasswordExpiredException;
import org.apache.catalina.authenticator.SavedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

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
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String updatepassword=encoder.encode(user.getPassword());
        user.setUpdatepassword(updatepassword);
        //開始設置其他後台參數，時間，使用者等等
        user.setIsDelete(0);
        user.setCreated_user(username);
        user.setCreated_time(now);
        user.setModified_user(username);
        user.setModified_time(now);
        user.setRole("ROLE_USER");
        user.setEnabled(1);

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
            throw new UsernameNotFoundException("登入失敗，找不到帳號");
        }

        if(user.getIsDelete()==1){
            throw new UsernameNotFoundException("登入失敗，帳號已經被刪除");
        }

        //如果加密結果一致
        //加密演算法是一種可逆的演算法，基本過程就是對原來為明文的檔案或資料按某種演算法進行處理，使其成為不可讀的一段程式碼為“密文”，但在用相應的金鑰進行操作之後就可以得到原來的內容 。
        //雜湊演算法是一種不可逆的演算法，是把任意長度的輸入通過雜湊演算法變換成固定長度的輸出,輸出就是雜湊值，不同的輸入可能會雜湊成相同的輸出，所以不可能從雜湊值來確定唯一的輸入值。
        //BCryptPasswordEncoder加密方式是將原密碼加密成60字元，如果要進行校驗，使用BCryptPasswordEncoder提供的方法
        //boolean matches(CharSequence rawPassword,String encodedPassword); （明文密碼，加密密碼）
        //正確的話返回true，錯誤的話返回false，所以要進去if裏面代表錯誤要！
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        if(!encoder.matches(password,user.getUpdatepassword())){
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
            throw new UsernameNotFoundException("獲取數據失敗，請再重新嘗試一次");
        }
        //新增陣列，來接受userDao.ReadByUsername的回傳資料
        List<User> list=new ArrayList<>();
        list=userDao.ReadByUid(uid);
        //因為只會有一筆，所以在陣列的第0個位置
        User olduser= list.get(0);

        if(olduser.getIsDelete()==1){
            throw new UsernameNotFoundException("登入失敗，帳號已經被刪除");
        }

        olduser.setEmail(user.getEmail());
        olduser.setPhone(user.getPhone());
        olduser.setGender(user.getGender());
        olduser.setModified_time(new Date());
        olduser.setModified_user(user.getModified_user());

        return userDao.UpdateByUid(uid,olduser);
    }
}
