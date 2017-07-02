package com.wenda.async;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 49540 on 2017/6/30.
 */
public class EventModel {
    private EventType eventType;//事件类型
    private int entityId;//事件操作的实体id
    private int entityType;//事件操作的类型（如评论，问题等）
    private int actorId;//触发对象的实体，即当前用户
    private int entityOwnerId;//跟实体相关的对象

    private Map<String,String> exts = new HashMap<String,String>();


    public EventModel()
    {
    }
    public EventModel(EventType eventType)
    {
        this.eventType = eventType;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }

    public String getExt(String key)
    {

        return exts.get(key);
    }
    public EventModel setExt(String key,String value)
    {
        exts.put(key, value);
        return this;
    }

    public EventType getEventType() {
        return eventType;
    }

    public EventModel setEventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

}
