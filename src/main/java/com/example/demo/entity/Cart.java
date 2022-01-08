package com.example.demo.entity;

import lombok.Data;

//購物車的實體類別
@Data
public class Cart extends BaseEntity {

    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Integer num;
    private Integer price;

}
