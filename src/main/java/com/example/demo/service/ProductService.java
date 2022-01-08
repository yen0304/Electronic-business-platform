package com.example.demo.service;


import com.example.demo.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getHostList();

    //查詢商品詳細數據
    Product ReadById(Integer id);

}
