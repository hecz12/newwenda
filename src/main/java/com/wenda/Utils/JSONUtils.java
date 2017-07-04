package com.wenda.Utils;

import com.alibaba.fastjson.JSONObject;
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
