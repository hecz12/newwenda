package com.wenda.Utils;

import com.wenda.model.EntityType;
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
    //关注对象
    public static final String BIZ_FOLLOWEE = "FOLLOWEE";
    //粉丝对象
    public static final String BIZ_FOLLOWER = "FOLLOWER";


    public static String getFolloweeKey(int entityType,int entityId)
    {
        return BIZ_FOLLOWEE+SIPLT+String.valueOf(entityType)+String.valueOf(entityId);
    }

    public static String getFollowerKey(int entityType,int entityId)
    {
        return BIZ_FOLLOWER+SIPLT+String.valueOf(entityType)+String.valueOf(entityId);
    }

    /**
     * 获取对应问题和评论的赞
     * @param entityId
     * @param entityType
     * @return
     */
    public static String getLikeKey(int entityId,int entityType)
    {
        return BIZ_LIKE+SIPLT+String.valueOf(entityId)+SIPLT+String.valueOf(entityType);
    }

    /**
     * 获取对应问题和评论的反对
     * @param entityId
     * @param entityType
     * @return
     */
    public static String getDislikeKey(int entityId,int entityType)
    {
        return BIZ_DISLIKE+SIPLT+String.valueOf(entityId)+SIPLT+String.valueOf(entityType);
    }
    public static String getEventKey()
    {
        return EVENT_KEY;
    }
}
