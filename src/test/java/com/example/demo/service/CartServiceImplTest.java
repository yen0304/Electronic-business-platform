package com.example.demo.service;

import com.example.demo.entity.CartVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CartServiceImplTest {

    @Autowired
    private CartService cartService;

    @Test
    public void addToCart() {
        System.out.println("2");
        Integer uid = 1;
        String username = "用戶1";
        Integer pid = 3;
        Integer amount = 5;
        cartService.AddToCart(uid, username, pid, amount);
        System.out.println("OK.");
    }

    @Test
    public void getByUid() {
        Integer uid = 1;
        List<CartVO> list = cartService.GetByUid(uid);
        System.out.println("count=" + list.size());
        for (CartVO item : list) {
            System.out.println(item);
        }
    }

    @Test
    public void addNum() {
        Integer cid = 3;
        Integer uid = 3;
        String username = "abc123";
        cartService.AddNum(cid, uid, username);
        System.out.println("OK.");
    }

}