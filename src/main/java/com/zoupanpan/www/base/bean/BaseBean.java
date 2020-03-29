package com.zoupanpan.www.base.bean;

/**
 * @author zoupanpan
 * @version 2020/3/22 0:24
 */
public abstract class BaseBean {

    public static Integer TRUE_INT = 1;
    public static Integer FALSE_INT = 0;

    public BaseBean() {
        result = ResultBean.SUCCESS;
    }

    private ResultBean result;


    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }
}
