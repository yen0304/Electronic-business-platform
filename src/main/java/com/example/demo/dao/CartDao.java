package com.example.demo.dao;

import com.example.demo.entity.Cart;
import com.example.demo.entity.CartVO;


import java.util.Date;
import java.util.List;

public interface CartDao {

    Integer CreateCart(Cart cart);

    //判斷購物車有無商品
    List<Cart> FindByUidandPid(Integer uid,Integer pid);

    //更新購物車數量
    Integer UpdateNumByCid(Integer cid, Integer num, String modified_user, Date modified_time);

    List<CartVO> FindByUid(Integer uid);

    //根據cid
    Cart FindByCid(Integer cid);




}
