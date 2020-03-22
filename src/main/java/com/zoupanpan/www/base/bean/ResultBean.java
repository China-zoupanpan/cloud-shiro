package com.zoupanpan.www.base.bean;


/**
 * @author zoupanpan
 * @version 2020/3/22 0:26
 */

public class ResultBean {


    public final static ResultBean SUCCESS = new ResultBean(0,null);

    public final static ResultBean FAILURE = new ResultBean(-1,null);

    private ResultBean(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final Integer code;

    private final String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
