package com.example.demo.service;


import com.example.demo.Exception.ProductNotFoundException;
import com.example.demo.dao.ProductDao;
import com.example.demo.dao.ProductDaoImpl;
import com.example.demo.entity.Product;
import com.example.demo.mapper.ProductRowMapper;
import org.springframework.beans.PropertyAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService{


    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getHostList() {
        List<Product> products = findHostList();

        System.out.println(products);
        return products;
    }

    @Override
    public Product ReadById(Integer id) {

        Product product=ReadById(id);
        if(product == null){
            throw new ProductNotFoundException("找不到商品數據");
        }
        return product;
    }

    private List<Product> findHostList() {
        return productDao.findHostList();
    }
}
