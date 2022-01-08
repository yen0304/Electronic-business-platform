package com.example.demo.controller;


import com.example.demo.entity.Cart;
import com.example.demo.entity.CartVO;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    //http://localhost:8080/carts/add?pid=1&amount=3
    @PostMapping("/addcart")
    public String AddToCart(Integer pid, Integer amount, HttpSession session){
        Integer uid =(int) session.getAttribute("uid");
        String username =(String) session.getAttribute("username");
        cartService.AddToCart(uid, username, pid, amount);
        return "購物車操作成功";
    }

    //http://localhost:8080/carts/
    @GetMapping("/")
    public List<CartVO> getByUid(HttpSession session) {
        //從seesion查詢uid數值
        Integer uid =(int) session.getAttribute("uid");
        //執行查詢並返回
        return cartService.GetByUid(uid);
    }

    //http://localhost:8080/carts/1/num/add
    @PostMapping("{cid}/num/add")
    public String addNum(@PathVariable("cid") Integer cid, HttpSession session) {
        Integer uid =(int) session.getAttribute("uid");
        String username =(String) session.getAttribute("username");

        return cartService.AddNum(cid, uid, username);
    }

}
