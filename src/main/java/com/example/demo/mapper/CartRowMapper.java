package com.example.demo.mapper;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartRowMapper implements RowMapper<Cart> {

    @Override
    public Cart mapRow(ResultSet resultSet, int i) throws SQLException {

       Cart cart=new Cart();
        //後面result.getxx()，()裡面放的是要取得的資料在資料庫中的名字
        cart.setCid(resultSet.getInt("cid"));
        cart.setUid(resultSet.getInt("uid"));
        cart.setPid(resultSet.getInt("pid"));
        cart.setNum(resultSet.getInt("num"));
        cart.setPrice(resultSet.getInt("price"));
        cart.setCreated_user(resultSet.getString("created_user"));
        cart.setCreated_time(resultSet.getDate("created_time"));
        cart.setModified_user(resultSet.getString("modified_user"));
        cart.setModified_time(resultSet.getDate("modified_time"));

        return cart;
    }
}
