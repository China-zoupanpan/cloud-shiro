package com.zoupanpan.www.base.advice;

/**
 * @author zoupanpan
 * @version 2020/5/31 13:11
 */
public enum LangHelper {
    INSTANCE;
    ThreadLocal<String> langLocal = new ThreadLocal<>();

    public void setLang(String lang) {
        langLocal.set(lang);
    }

    public String getLang() {
        return langLocal.get();
    }

    public void clear() {
        langLocal.remove();
    }
}
