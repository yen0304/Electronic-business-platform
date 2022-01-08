package com.example.demo.service;

import com.example.demo.Exception.CartNotFoundException;
import com.example.demo.dao.CartDao;
import com.example.demo.entity.Cart;
import com.example.demo.entity.CartVO;
import com.example.demo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CartServiceImpl implements CartService{

    @Autowired
    private CartDao cartDao;
    @Autowired
    private ProductService productService;


    //為購物車插入新的商品數據
    private void Create(Cart cart) {
        Integer rows = cartDao.CreateCart(cart);
        if (rows != 1) {
            throw new RuntimeException("創建購物車數據失敗");
        }
    }

    //查找購物車裡有無該商品
    private List<Cart> findByUidAndPid(Integer uid, Integer pid) {

        return cartDao.FindByUidandPid(uid,pid);
    }

    //更新購物車數據
    private void UpdateNumByCid(Integer cid, Integer num, String modified_user, Date modified_time) {

        Integer rows = cartDao.UpdateNumByCid(cid, num, modified_user, modified_time);
        if (rows != 1) {
            throw new RuntimeException("修改商品失敗");
        }
    }

    @Override
    public List<CartVO> GetByUid(Integer uid) {
        return FindByUid(uid);
    }

    private List<CartVO> FindByUid(Integer uid) {
        return cartDao.FindByUid(uid);
    }


    @Override
    public void AddToCart(Integer uid, String username, Integer pid, Integer amount) {

        //獲取目前時間
        Date now = new Date();
        //查詢購物車情況，看看購物車是否有該商品
        List<Cart> list = new ArrayList<>();
        list=findByUidAndPid(uid, pid);
        //判斷是否為Null
        if (list == null) {
            // 是，表示需要創建購物車
            //獲取商品id
            Product product = productService.ReadById(pid);
            //創建新的物件
            Cart cart = new Cart();
            //開始設置數據
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
            cart.setPrice(product.getPrice());
            cart.setCreated_user(username);
            cart.setCreated_time(now);
            cart.setModified_user(username);
            cart.setModified_time(now);

            //創建購物車
            Create(cart);
        } else {
            // 否，表示已經有了該傷品，要更新數量
            Integer cid = list.get(0).getCid();
            // 新數量=原數量 + 要加的新數量
            Integer num = list.get(0).getNum() + amount;
            // 更新
            UpdateNumByCid(cid, num, username, now);
        }


    }

    @Override
    public String AddNum(Integer cid, Integer uid, String username) {
        //根據cid查詢Cart數據
        Cart result = FindByCid(cid);
        //如果找不到數據
        if (result == null) {
            // 是：抛出CartNotFoundException
            throw new CartNotFoundException("購物車不存在");
        }

        //查詢的Uid與session中的uid不匹配
        if (!result.getUid().equals(uid)) {
            throw new CartNotFoundException("增加商品數量失敗，操作已被伺服器拒絕");
        }

        //新數量= 原數量 + 新數量
        Integer newNum = result.getNum() + 1;
        UpdateNumByCid(cid, newNum, username, new Date());

        return "新增成功";
    }


    private Cart FindByCid(Integer cid) {
        return cartDao.FindByCid(cid);
    }

}
