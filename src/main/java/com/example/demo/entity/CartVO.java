package com.example.demo.entity;


import lombok.Data;

import java.io.Serializable;

@Data
public class CartVO implements Serializable {


    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Integer num;
    private Integer price;
    private String title;
    private Integer realPrice;

}
