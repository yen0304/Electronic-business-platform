package com.example.demo.dao;

import com.example.demo.entity.Cart;
import com.example.demo.entity.CartVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartDaoImplTest {

    @Autowired
    private CartDao cartDao;

    @Test
    public void findByUidandPid() {
        Integer uid = 1;
        Integer pid = 2;
        List<Cart> result = cartDao.FindByUidandPid(uid,pid);
        System.out.println(result);
    }

    @Test
    public void createCart() {
        Cart cart = new Cart();
        cart.setUid(2);
        cart.setPid(3);
        cart.setNum(4);
        cart.setPrice(3999);

        Date now=new Date();
        cart.setModified_time(now);
        cart.setCreated_time(now);
        cart.setCreated_user("user");
        cart.setModified_user("user");

        Integer rows = cartDao.CreateCart(cart);
        System.out.println("rows=" + rows);
        System.out.println(cart);
    }

    @Transactional
    @Test
    public void updateNumByCid() {
        Integer cid = 1;
        Integer num = 10;
        String modifiedUser = "管理員";
        Date modifiedTime = new Date();

        Integer rows = cartDao.UpdateNumByCid(cid, num, modifiedUser, modifiedTime);
        System.out.println("rows=" + rows);
    }

    @Test
    public void findByUid() {
        Integer uid = 3;
        List<CartVO> list = cartDao.FindByUid(uid);
        System.out.println("count=" + list.size());
        for (CartVO item : list) {
            System.out.println(item);
        }
    }

    @Test
    public void findByCid(){
        Integer cid = 2;
        Cart cart = cartDao.FindByCid(cid);
        System.out.println("cart=" + cart);
    }

}