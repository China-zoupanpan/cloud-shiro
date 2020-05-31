package com.zoupanpan.www.cloudshiro;

import com.zoupanpan.www.base.ValidatorUtils;
import com.zoupanpan.www.login.bean.LoginParamBean;
import com.zoupanpan.www.login.bean.SubBean;

import java.util.Collections;

/**
 * @author zoupanpan
 * @version 2020/5/17 18:36
 */
public class ValidatorUtilsTest {

    public static void main(String[] args) {
        LoginParamBean paramBean = new LoginParamBean();
        paramBean.setUserName("");
        paramBean.setPassword("ksj是dflkj");
        paramBean.setSecret("ksjdflkj");
        paramBean.setToken("");
        paramBean.setValidateNum(999);
        paramBean.setValidateNum1(999);
        paramBean.setValidateNum2(999.2222);
        paramBean.setValidateNum3(333);
        paramBean.setEmailList(Collections.singletonList("e555445"));
        paramBean.setEmail("www.pp.comzoupanpan@163.com");
        paramBean.setSubBeanList(Collections.singletonList(new SubBean("")));
        System.out.println("===========1=====");
        ValidatorUtils.validate(paramBean);
        ValidatorUtils.validateFastFail(paramBean);
        System.out.println("===========2=====");
        ValidatorUtils.validate(paramBean, ValidatorUtils.AddGroup.class);
        ValidatorUtils.validateFastFail(paramBean, ValidatorUtils.AddGroup.class);
        System.out.println("===========3=====");
        ValidatorUtils.validate(paramBean, ValidatorUtils.AddGroup.class, ValidatorUtils.UpdateGroup.class);
        ValidatorUtils.validateFastFail(paramBean, ValidatorUtils.AddGroup.class, ValidatorUtils.UpdateGroup.class);
        System.out.println("===========4=====");
        ValidatorUtils.validate(paramBean, ValidatorUtils.UpdateGroup.class);
        ValidatorUtils.validateFastFail(paramBean, ValidatorUtils.UpdateGroup.class);


    }
}
