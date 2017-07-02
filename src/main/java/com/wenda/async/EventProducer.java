package com.wenda.async;

import com.alibaba.fastjson.JSONObject;
import com.wenda.Utils.JSONUtils;
import com.wenda.Utils.JedisAdapter;
import com.wenda.Utils.RedisKeyUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * Created by 49540 on 2017/6/30.
 */
@Service
public class EventProducer {
    public static  final Logger LOGGER = LoggerFactory.getLogger(EventProducer.class);
    @Autowired
    JedisAdapter jedisAdapter;


    public void fireEvent(EventModel eventModel)
    {
        try{
            String key = RedisKeyUtils.getEventKey();
            jedisAdapter.lpush(key, JSONObject.toJSONString(eventModel));
        }
        catch (Exception e)
        {
            LOGGER.error("发送事件失败");
        }
    }
}
