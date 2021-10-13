package com.example.demo.entity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/*全部實體類別的基底類別*/
//implements Serializable序列化
@Data
public class BaseEntity implements Serializable {

    private String created_user ;
    private Date created_time;
    private String modified_user;
    private Date modified_time;
}
