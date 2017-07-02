package com.wenda.service;

import com.wenda.Utils.JedisAdapter;
import com.wenda.Utils.RedisKeyUtils;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.InvalidRelationTypeException;

/**
 * Created by 49540 on 2017/6/29.
 */
@Service
public class LikeService {
    @Autowired
    private JedisAdapter jedisAdapter;

    public long like(int userId,int entityId,int entityType)
    {
        String likeKey = RedisKeyUtils.getLikeKey(entityId, entityType);
        jedisAdapter.sadd(likeKey,String.valueOf(userId));
        String dislikeKey = RedisKeyUtils.getDislikeKey(entityId,entityType);
        jedisAdapter.srem(dislikeKey,String.valueOf(userId));
        return jedisAdapter.scard(likeKey);
    }

    public long dislike(int userId, int entityId,int entityType)
    {
        String likeKey = RedisKeyUtils.getLikeKey(entityId, entityType);
        jedisAdapter.srem(likeKey,String.valueOf(userId));
        String dislikeKey = RedisKeyUtils.getDislikeKey(entityId,entityType);
        jedisAdapter.sadd(dislikeKey,String.valueOf(userId));
        return jedisAdapter.scard(likeKey);
    }

    public long getLikeCount(int entityId,int entityType)
    {
        String likeKey = RedisKeyUtils.getLikeKey(entityId, entityType);
        return jedisAdapter.scard(likeKey);
    }

    public int  isremember(int userId,int entityId,int entityType)
    {
        String likeKey = RedisKeyUtils.getLikeKey(entityId, entityType);
        String dislikeKey = RedisKeyUtils.getDislikeKey(entityId,entityType);
        if(jedisAdapter.isremember(likeKey,String.valueOf(userId)))
        {
            return 1;
        }
        return jedisAdapter.isremember(dislikeKey,String.valueOf(userId))?-1:0;
    }
}
