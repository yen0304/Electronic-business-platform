package com.example.demo.dao;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserImpl implements UserDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public String CreateUser(User user) {
        String sql ="INSERT INTO t_user" +
                "(username,password,updatepassword,gender,phone,email,avatar,is_delete,created_user,created_time,modified_user,modified_time,role ,enabled)" +
                "VALUE" +
                "(:userName,:userPassword,:Updatepassword,:Gender,:Phone,:Email,:Avatar,:Is_delete,:Create_user,:Create_time,:Modified_user,:Modified_time,:Role ,:Enabled)";

        Map<String, Object> map =new HashMap<>();
        map.put("userName",user.getUsername());
        map.put("userPassword",user.getPassword());
        map.put("Updatepassword",user.getUpdatepassword());
        map.put("Gender",user.getGender());
        map.put("Phone",user.getPhone());
        map.put("Email",user.getEmail());
        map.put("Avatar",user.getAvatar());
        map.put("Is_delete",user.getIsDelete());
        map.put("Create_user",user.getCreated_user());
        map.put("Create_time",user.getCreated_time());
        map.put("Modified_user",user.getModified_user());
        map.put("Modified_time",user.getModified_time());
        map.put("Role",user.getRole());
        map.put("Enabled",user.getEnabled());

        namedParameterJdbcTemplate.update(sql,map);
        return ("註冊成功");
    }

    @Override
    public List<User> ReadByUsername(String username) {
        String sql="SELECT * FROM t_user WHERE username=:userName";

        Map<String,Object>map= new HashMap<>();
        map.put("userName",username);

        List<User> list =namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());

        if(list.size()>0){
            return list;
        }else {
            //如果是空集合[]
            return null;
        }
    }

    @Override
    public List<User> ReadByUid(Integer uid) {
        String sql="SELECT * FROM t_user WHERE uid=:Uid";

        Map<String,Object>map= new HashMap<>();
        map.put("Uid",uid);

        List<User> list =namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());

        if(list.size()>0){
            return list;
        }else {
            //如果是空集合[]
            return null;
        }
    }

    @Override
    public String UpdateByUid(Integer uid, User user) {

        String sql ="UPDATE t_user SET " +
                "phone=:Phone,email=:Email,gender=:Gender ,modified_user=:Modified_user,modified_time=:Modified_time" +
                " WHERE uid=:Uid";

        Map<String, Object> map =new HashMap<>();
        map.put("Gender",user.getGender());
        map.put("Phone",user.getPhone());
        map.put("Email",user.getEmail());
        map.put("Modified_user",user.getModified_user());
        map.put("Modified_time",user.getModified_time());
        map.put("Uid",uid);
        namedParameterJdbcTemplate.update(sql,map);

        return "修改成功";
    }
}
