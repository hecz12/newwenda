package com.wenda.Utils;

import com.alibaba.fastjson.JSONObject;
import com.sun.javafx.collections.MappingChange;

import java.util.Map;

import java.util.Map;
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
    public static String getJSONString(Map<String,String> map)
    {
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String,String> entry:map.entrySet())
        {
            jsonObject.put(entry.getKey(),entry.getValue());
        }
        return jsonObject.toJSONString();
    }

    public static String getJSONString(int code, Map<String,Object> map)
    {
        JSONObject json = new JSONObject();
        json.put("code", code);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            json.put(entry.getKey(), entry.getValue());
        }
        return json.toJSONString();
    }

    public static String getJSONString(int code)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        return jsonObject.toJSONString();
    }
}
