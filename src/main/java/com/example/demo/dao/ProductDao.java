package com.example.demo.dao;

import com.example.demo.entity.Product;

import java.util.List;



public interface ProductDao {

    //商品熱門排行（前三）
    List<Product> findHostList();

    //商品詳細數據
    Product ReadById(Integer id);
}
