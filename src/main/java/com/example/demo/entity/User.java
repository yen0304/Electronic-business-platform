package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;


@Data
public class User extends BaseEntity implements Serializable {

    private Integer uid;
    private String username;
    private String password;
    private String updatepassword;
    private Integer gender;
    private String phone;
    private String email;
    private String avatar;
    private Integer isDelete;
    private String role;
    private Integer enabled;
}
