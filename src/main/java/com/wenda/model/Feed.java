package com.wenda.model;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * Created by 49540 on 2017/7/7.
 */
public class Feed {
    private int id;
    private int userId;
    private Date createDate;
    private int type;
    private String data;
    private JSONObject jsonData;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        jsonData = JSONObject.parseObject(data);
    }

    public Object get(String key)
    {
        return jsonData.get(key);
    }
}
