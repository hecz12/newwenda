package com.wenda.model;

import java.rmi.MarshalledObject;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by 49540 on 2017/6/26.
 */
public class ViewObject {
    private HashMap<String,Object> map = new HashMap();
    public void set(String key,Object value)
    {
        map.put(key,value);
    }
    public Object get(String key)
    {
        return map.get(key);
    }
}
