package com.example.demo.dao;

import com.example.demo.entity.Cart;
import com.example.demo.entity.CartVO;
import com.example.demo.entity.Product;
import com.example.demo.mapper.CartRowMapper;
import com.example.demo.mapper.CartVORowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CartDaoImpl implements CartDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Cart> FindByUidandPid(Integer uid, Integer pid) {
        String sql="SELECT * FROM t_cart WHERE uid=:Uid AND pid=:Pid";
        Map<String,Object> map= new HashMap<>();
        map.put("Uid",uid);
        map.put("Pid",pid);

        List<Cart> list =namedParameterJdbcTemplate.query(sql,map,new CartRowMapper());

        if(list.size()>0){
            return list;
        }else {
            //如果是空集合[]
            return null;
        }
    }

    @Override
    public Integer CreateCart(Cart cart) {
        String sql= "INSERT INTO t_cart" +
                "(uid, pid, num, price, created_user, created_time, modified_user, modified_time)" +
                "VALUES " +
                "(:Uid, :Pid, :Num, :Price,:Created_user, :Created_time, :Modified_user, :Modified_time)";

        Map<String,Object> map= new HashMap<>();
        map.put("Uid",cart.getUid());
        map.put("Pid",cart.getPid());
        map.put("Num",cart.getNum());
        map.put("Price",cart.getPrice());
        map.put("Created_user",cart.getCreated_user());
        map.put("Created_time",cart.getCreated_time());
        map.put("Modified_user",cart.getModified_user());
        map.put("Modified_time",cart.getModified_time());

        int rows=namedParameterJdbcTemplate.update(sql,map);
        return rows;
    }

    @Override
    public Integer UpdateNumByCid(Integer cid, Integer num, String modified_user, Date modified_time) {
        String sql="UPDATE t_cart SET num=:Num, modified_user=:Modified_user, " +
                "modified_time=:Modified_time where cid=:Cid";

        Map<String,Object> map= new HashMap<>();
        map.put("Num",num);
        map.put("Cid",cid);
        map.put("Modified_user",modified_user);
        map.put("Modified_time",modified_time);

        int rows=namedParameterJdbcTemplate.update(sql,map);
        return rows;

    }

    @Override
    public List<CartVO> FindByUid(Integer uid) {
        String sql="SELECT cid, uid, pid, t_cart.num, t_cart.price," +
                "title, t_product.price AS realPrice FROM " +
                "t_cart LEFT JOIN t_product ON " +
                "t_cart.pid=t_product.id " +
                "WHERE uid=:Uid ORDER BY t_cart.created_time desc";

        Map<String,Object> map= new HashMap<>();
        map.put("Uid",uid);

        List<CartVO> list =namedParameterJdbcTemplate.query(sql,map,new CartVORowMapper());

        if(list.size()>0){
            return list;
        }else {
            //如果是空集合[]
            return null;
        }
    }

    @Override
    public Cart FindByCid(Integer cid) {
        String sql="SELECT * FROM t_cart WHERE cid=:Cid";
        Map<String,Object> map= new HashMap<>();
        map.put("Cid",cid);
        List<Cart> list =namedParameterJdbcTemplate.query(sql,map,new CartRowMapper());


        if(list.size()>0){
            return list.get(0);
        }else {
            //如果是空集合[]
            return null;
        }
    }
}
