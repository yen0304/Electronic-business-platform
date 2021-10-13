package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {

        User user=new User();

        //後面result.getxx()，()裡面放的是要取得的資料在資料庫中的名字
        user.setUid(resultSet.getInt("uid"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setUpdatepassword(resultSet.getString("updatepassword"));
        user.setGender(resultSet.getInt("gender"));
        user.setPhone(resultSet.getString("phone"));
        user.setEmail(resultSet.getString("email"));
        user.setAvatar(resultSet.getString("avatar"));
        user.setIsDelete(resultSet.getInt("is_delete"));
        user.setCreated_user(resultSet.getString("created_user"));
        user.setCreated_time(resultSet.getTime("created_time"));
        user.setModified_user(resultSet.getString("modified_user"));
        user.setModified_time(resultSet.getTime("modified_time"));

        user.setRole(resultSet.getString("role"));
        user.setEnabled(resultSet.getInt("enabled"));
        return user;
    }
}
