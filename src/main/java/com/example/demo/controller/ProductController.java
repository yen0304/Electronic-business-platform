package com.example.demo.controller;


import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import com.example.demo.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static javax.security.auth.callback.ConfirmationCallback.OK;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    //
    @GetMapping("/list/hot")
    public List<Product> getHostList(){
        return productService.getHostList();
    }

    @GetMapping
    public Product ReadById(@RequestParam Integer id){
        return productService.ReadById(id);
    }
}
