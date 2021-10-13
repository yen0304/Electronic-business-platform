package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;


@Data
public class Product extends BaseEntity implements Serializable {
    private Integer id;
    private Integer categoryId;
    private String type;
    private String title;
    private Integer price;
    private Integer num;
    private String imagepath;
    private Integer status;
    private Integer priority;
}
