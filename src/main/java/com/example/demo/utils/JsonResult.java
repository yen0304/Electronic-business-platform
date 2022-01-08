package com.example.demo.utils;


import lombok.Data;

@Data
public class JsonResult<E> {

    //返回的狀態碼
    private Integer state;
    //返回時要顯示的訊息
    private String message;
    //成功時要返回的數據
    private E data;

    public JsonResult() {
        super();
    }

    public JsonResult(Integer state) {
        super();
        this.state = state;
    }

    public JsonResult(Throwable e) {
        super();
        this.message = e.getMessage();
    }

    public JsonResult(Integer state, E data) {
        super();
        this.state = state;
        this.data = data;
    }

    public JsonResult(String message, E data) {
        super();
        this.message=message;
        this.data = data;
    }

}
