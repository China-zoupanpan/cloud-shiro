package com.zoupanpan.www.base.bean;


/**
 * @author zoupanpan
 * @version 2020/3/22 0:26
 */

public class ResultBean<T> {


    public static final ResultBean SUCCESS = new ResultBean(0, null);

    //public final static ResultBean FAILURE = new ResultBean(-1,null);

    private ResultBean(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ResultBean(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResultBean SUCCESS() {
        return new ResultBean(0, null, null);
    }

    public static <T> ResultBean SUCCESS(T data) {
        return new ResultBean(0, null, data);
    }

    public static <T> ResultBean SUCCESS(String msg, T data) {
        return new ResultBean(0, msg, data);
    }

    public static ResultBean FAILURE(Integer code, String msg) {
        return new ResultBean(code, msg);
    }

    public static <T> ResultBean FAILURE(Integer code, String msg, T data) {
        return new ResultBean(code, msg, data);
    }

    private Integer code;

    private String msg;

    private T data;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
