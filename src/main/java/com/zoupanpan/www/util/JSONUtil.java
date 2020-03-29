package com.zoupanpan.www.util;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.session.mgt.SimpleSession;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 本地实现 方便切换json实现
 *
 * @author zoupanpan
 * @version 2020/3/28 10:55
 */
public class JSONUtil {

    public static <T> T parseObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }


    public static String writeObjectToJson(Object obj) {
        return JSON.toJSONString(obj);
    }

    public static void main(String[] args) {
        SimpleSession simpleSession = new SimpleSession();
        OutputStream os = new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }
        };
//        ObjectOutputStream oos = new ObjectOutputStream();
//        simpleSession.writeObject(oos);
    }

}
