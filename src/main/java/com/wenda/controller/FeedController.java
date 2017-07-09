package com.wenda.controller;

import com.wenda.Utils.JedisAdapter;
import com.wenda.Utils.RedisKeyUtils;
import com.wenda.model.EntityType;
import com.wenda.model.Feed;
import com.wenda.model.HostHolder;
import com.wenda.model.User;
import com.wenda.service.FeedService;
import com.wenda.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 49540 on 2017/7/8.
 */
@Controller
public class FeedController {
    @Autowired
    JedisAdapter jedisAdapter;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    FeedService feedService;
    @Autowired
    FollowService followService;

    @RequestMapping(path = {"/pushfeeds"},method = {RequestMethod.GET,RequestMethod.POST})
    public String pushFeeds(Model model)
    {
        int localUserId = hostHolder.get()==null?0:hostHolder.get().getId();
        String key = RedisKeyUtils.getTimelineKey(localUserId);
        List<String> feedIds = jedisAdapter.lrange(key,0,10);
        List<Feed> feeds = new ArrayList();
        for(String feedId:feedIds)
        {
            System.out.println(feedId);
            Feed feed = feedService.getById(Integer.valueOf(feedId));
            feeds.add(feed);
        }
        model.addAttribute("feeds",feeds);
        return "feeds";
    }

    @RequestMapping(path = {"/pullfeeds"},method = {RequestMethod.GET,RequestMethod.POST})
    public String pullFeeds(Model model)
    {
        int localUserId = hostHolder.get()==null?0:hostHolder.get().getId();
        if(localUserId==0)
        {
            List<Feed> feeds = feedService.getFeeds(0, 10);
            model.addAttribute("feeds",feeds);
            return "feeds";
        }
        List<Integer> list = followService.getFollowees(localUserId, EntityType.TYPE_USER
                                                        ,10);
        List<Feed> userFeeds = feedService.getUserFeeds(Integer.MAX_VALUE, list, 10);
        model.addAttribute("feeds",userFeeds);
        return "feeds";
    }
}
