package com.example.demo.utils;

import java.util.UUID;

//https://www.jianshu.com/p/2c62ae005db4
public class UUIDutil {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
