package com.zoupanpan.www.base.webmvc;

/**
 * @author zoupanpan
 * @version 2020/5/31 12:26
 */
public class SimpleException extends RuntimeException {

    private int code;

    public SimpleException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
