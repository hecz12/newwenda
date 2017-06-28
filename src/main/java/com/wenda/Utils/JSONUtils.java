package com.wenda.Utils;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * Created by 49540 on 2017/6/28.
 */
public class JSONUtils {

    public static String getJSONString(int code,String msg)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("msg",msg);
        return jsonObject.toJSONString();
    }

    public static String getJSONString(int code)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        return jsonObject.toJSONString();
    }
}
