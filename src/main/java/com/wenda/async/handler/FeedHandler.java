package com.wenda.async.handler;

import com.sun.javafx.collections.MappingChange;
import com.wenda.Utils.JSONUtils;
import com.wenda.Utils.JedisAdapter;
import com.wenda.Utils.RedisKeyUtils;
import com.wenda.async.EventHandler;
import com.wenda.async.EventModel;
import com.wenda.async.EventType;
import com.wenda.model.*;
import com.wenda.service.FeedService;
import com.wenda.service.FollowService;
import com.wenda.service.QuestionService;
import com.wenda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Map;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 49540 on 2017/7/8.
 */
@Service
public class FeedHandler implements EventHandler {
    @Autowired
    HostHolder hostHolder;
    @Autowired
    FeedService feedService;
    @Autowired
    QuestionService questionService;
    @Autowired
    FollowService followService;
    @Autowired
    JedisAdapter jedisAdapter;
    @Autowired
    UserService userService;

    public String getDate(EventModel eventModel)
    {
        Map<String,String> map = new HashMap();
        User actor = userService.getUser(eventModel.getActorId());
        if(actor==null)
        {
            return null;
        }
        map.put("userId",String.valueOf(actor.getId()));
        map.put("username",String.valueOf(actor.getName()));
        map.put("headUrl",String.valueOf(actor.getHeadUrl()));
        if(eventModel.getEventType()==EventType.COMMENT||
                (eventModel.getEventType()==EventType.FOLLOW&&
                eventModel.getEntityType()== EntityType.TYPE_QUESTION))
        {
            Question question = questionService.getQuestionById(eventModel.getEntityId());
            if(question==null)
            {
                System.out.println("问题id错误");
                return  null;
            }
            map.put("questionId",String.valueOf(eventModel.getEntityId()));
            map.put("questionTitle",question.getTitle());
            return JSONUtils.getJSONString(map);
        }
        return null;
    }
    @Override
    public void dohandle(EventModel eventModel) {
        Feed feed = new Feed();
        feed.setCreateDate(new Date());
        feed.setUserId(eventModel.getActorId());
        feed.setType(eventModel.getEventType().getValue());
        feed.setData(getDate(eventModel));
        if(feed.getData()==null)
        {
            return ;
        }
        feedService.addFeed(feed);

        List<Integer> followers = followService.getFollowers(eventModel.getActorId(),
                                                            EntityType.TYPE_USER,
                                                            Integer.MAX_VALUE);
        //系统队列
        followers.add(0);
        for(int id:followers)
        {
            String timeLineKey = RedisKeyUtils.getTimelineKey(id);
            jedisAdapter.lpush(timeLineKey,String.valueOf(feed.getId()));
        }
    }

    @Override
    public List<EventType> getSupportEventType() {
        EventType[] array = {EventType.FOLLOW,EventType.COMMENT};
        return Arrays.asList(array);
    }
}
