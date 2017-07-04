package com.wenda.service;

import com.sun.deploy.panel.ITreeNode;
import com.sun.org.apache.xerces.internal.dom.EntityImpl;
import com.wenda.Utils.JedisAdapter;
import com.wenda.Utils.RedisKeyUtils;
import com.wenda.model.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by 49540 on 2017/7/3.
 */
@Service
public class FollowService {
    @Autowired
    JedisAdapter jedisAdapter;

    /**
     * 关注用户或者问题
     * @param userId
     * @param entityId
     * @param entityType
     * @return
     */
    public boolean follow(int userId,int entityId,int entityType)
    {
        Date date = new Date();
        String followeeKey = RedisKeyUtils.getFolloweeKey(entityType,userId);
        String followerKey = RedisKeyUtils.getFollowerKey(entityType, entityId);
        Jedis jedis = jedisAdapter.getJedis();
        Transaction tx = jedis.multi();
        //向关注列表中添加问题
        tx.zadd(followeeKey,date.getTime(),String.valueOf(entityId));
        //向粉丝列表中添加用户
        tx.zadd(followerKey,date.getTime(),String.valueOf(userId));
        List<Object> ret = tx.exec();
        if(ret!=null&&ret.size()==2&&((Long)ret.get(0)>0)&&((Long)ret.get(1)>0))
        {
            return true;
        }
        return false;
    }

    /**
     * 对实体取消关注
     * @param userId
     * @param entityId
     * @param entityType
     * @return
     */
    public boolean unfollow(int userId,int entityId,int entityType)
    {
        String followeeKey = RedisKeyUtils.getFolloweeKey(entityType,userId);
        String followerKey = RedisKeyUtils.getFollowerKey(entityType, entityId);
        Jedis jedis = jedisAdapter.getJedis();
        Transaction tx = jedis.multi();
        //向关注列表中添加问题
        tx.zrem(followeeKey,String.valueOf(entityId));
        //向粉丝列表中添加用户
        tx.zrem(followerKey,String.valueOf(userId));
        List<Object> ret = tx.exec();
        if(ret!=null&&ret.size()==2&&((Long)ret.get(0)>0)&&((Long)ret.get(1)>0))
        {
            return true;
        }
        return false;
    }
    //获取关注者数量
    public long getFollowerCount(int entityId,int entityType)
    {
        String key = RedisKeyUtils.getFollowerKey(entityType,entityId);
        return jedisAdapter.zcard(key);
    }

    public long getFolloweeCount(int userId,int entityType)
    {
        String key = RedisKeyUtils.getFolloweeKey(entityType,userId);
        return jedisAdapter.zcard(key);
    }

    public List<Integer> getFollowers(int entityId,int entityType,int offset,int count)
    {
        String key = RedisKeyUtils.getFollowerKey(entityType,entityId);
        return strConvertInt(jedisAdapter.zrevrange(key,offset,offset+count));
    }
    public List<Integer> getFollowers(int entityId,int entityType,int count)
    {
        String key = RedisKeyUtils.getFollowerKey(entityType,entityId);
        return strConvertInt(jedisAdapter.zrevrange(key,0,count));
    }

    public List<Integer> getFollowees(int userId,int entityType,int offset,int count)
    {
        String key = RedisKeyUtils.getFolloweeKey(entityType,userId);
        return strConvertInt(jedisAdapter.zrevrange(key,offset,offset+count));
    }
    public List<Integer> getFollowees(int userId,int entityType,int count)
    {
        String key = RedisKeyUtils.getFolloweeKey(entityType, userId);
        return strConvertInt(jedisAdapter.zrevrange(key,0,count));
    }

    /**
     * 判断用户是否关注了某个实体
     * @param userId
     * @param entityId
     * @param entityType
     * @return
     */
    public boolean isfollowers(int userId,int entityId,int entityType)
    {
        String key = RedisKeyUtils.getFollowerKey(entityType,entityId);
        return jedisAdapter.zscore(key,String.valueOf(userId))!=null;
    }



    public List<Integer> strConvertInt(Set<String> strs)
    {
        List<Integer> integers = new ArrayList<>();
        for(String str:strs)
        {
            integers.add(Integer.valueOf(str));
        }
        return integers;
    }
}
