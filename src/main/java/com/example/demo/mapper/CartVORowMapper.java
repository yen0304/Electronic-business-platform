package com.example.demo.mapper;

import com.example.demo.entity.Cart;
import com.example.demo.entity.CartVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartVORowMapper implements RowMapper<CartVO> {

    @Override
    public CartVO mapRow(ResultSet resultSet, int i) throws SQLException {

        CartVO cartVO=new CartVO();
        //後面result.getxx()，()裡面放的是要取得的資料在資料庫中的名字
        cartVO.setCid(resultSet.getInt("cid"));
        cartVO.setUid(resultSet.getInt("uid"));
        cartVO.setPid(resultSet.getInt("pid"));
        cartVO.setNum(resultSet.getInt("num"));
        cartVO.setPrice(resultSet.getInt("price"));
        cartVO.setTitle(resultSet.getString("title"));
        cartVO.setRealPrice(resultSet.getInt("realPrice"));


        return cartVO;
    }
}
