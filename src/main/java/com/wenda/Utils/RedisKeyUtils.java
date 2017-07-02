package com.wenda.Utils;

import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * 获取redis中key的名称
 * Created by 49540 on 2017/6/29.
 */
public class RedisKeyUtils {
    public static final String SIPLT = ":";
    public static final String BIZ_LIKE = "LIKE";
    public static final String BIZ_DISLIKE = "DISLIKE";
    public static final String EVENT_KEY = "EVENT_KEY";

    public static String getLikeKey(int entityId,int entityType)
    {
        return BIZ_LIKE+SIPLT+String.valueOf(entityId)+SIPLT+String.valueOf(entityType);
    }

    public static String getDislikeKey(int entityId,int entityType)
    {
        return BIZ_DISLIKE+SIPLT+String.valueOf(entityId)+SIPLT+String.valueOf(entityType);
    }
    public static String getEventKey()
    {
        return EVENT_KEY;
    }
}
