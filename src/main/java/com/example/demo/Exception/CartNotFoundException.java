package com.example.demo.Exception;




public class CartNotFoundException extends RuntimeException{

    public CartNotFoundException(String message) {
        super(message);
    }

}
