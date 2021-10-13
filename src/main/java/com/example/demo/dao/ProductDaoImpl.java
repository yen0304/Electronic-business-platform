package com.example.demo.dao;


import com.example.demo.entity.Product;
import com.example.demo.mapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao{


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Product> findHostList() {
        String sql="SELECT * FROM t_product where status=1 order by priority desc limit 0,3";
        Map<String,Object> map= new HashMap<>();

        List<Product> list =namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());

        if(list.size()>0){
            return list;
        }else {
            //如果是空集合[]
            return null;
        }
    }

    @Override
    public Product ReadById(Integer id) {
        String sql="SELECT * FROM t_product where id=:Id";
        Map<String,Object> map= new HashMap<>();
        map.put("Id",id);

        List<Product> list =namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());

        if(list.size()>0){
            return list.get(0);
        }else {
            //如果是空集合[]
            return null;
        }
    }
}
