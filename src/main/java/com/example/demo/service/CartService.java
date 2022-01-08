package com.example.demo.service;

import com.example.demo.entity.Cart;
import com.example.demo.entity.CartVO;

import java.util.Date;
import java.util.List;

public interface CartService {


    //更新購物車數量
    void AddToCart(Integer uid, String username, Integer pid, Integer amount);

    //購物車依user id顯示列表
    List<CartVO> GetByUid(Integer uid);

    String AddNum(Integer cid, Integer uid, String username);
}
