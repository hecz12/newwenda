package com.wenda.controller;

import com.wenda.Utils.JSONUtils;
import com.wenda.Utils.WendaUtils;
import com.wenda.async.EventModel;
import com.wenda.async.EventProducer;
import com.wenda.async.EventType;
import com.wenda.model.*;
import com.wenda.service.CommentService;
import com.wenda.service.FollowService;
import com.wenda.service.QuestionService;
import com.wenda.service.UserService;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 49540 on 2017/7/3.
 */
@Controller
public class FollowController {

    @Autowired
    FollowService followService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    EventProducer eventProducer;
    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;

    @RequestMapping(path = {"/followUser"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String followUser(@RequestParam("userId") int userId)
    {
        User user = hostHolder.get();
        if(user==null)
        {
            return JSONUtils.getJSONString(999);
        }
        boolean ret = followService.follow(user.getId(),userId, EntityType.TYPE_USER);
        eventProducer.fireEvent(new EventModel(EventType.FOLLOW).setActorId(user.getId())
                                .setEntityId(userId).setEntityType(EntityType.TYPE_USER)
                                .setEntityOwnerId(userId).setExt("username",user.getName())
        );
        return JSONUtils.getJSONString(ret?0:1,String.valueOf(
                followService.getFollowerCount(userId,EntityType.TYPE_USER)));
    }

    @RequestMapping(path = {"/unfollowUser"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String unfollowUser(@RequestParam("userId") int userId)
    {
        User user = hostHolder.get();
        if(user==null)
        {
            return JSONUtils.getJSONString(999);
        }
        boolean ret = followService.unfollow(user.getId(),userId, EntityType.TYPE_USER);
        eventProducer.fireEvent(new EventModel(EventType.UNFOLLOW).setActorId(user.getId())
                .setEntityId(userId).setEntityType(EntityType.TYPE_USER).setEntityOwnerId(userId)
                .setExt("username",user.getName()));
        return JSONUtils.getJSONString(ret?0:1,String.valueOf(
                followService.getFollowerCount(userId,EntityType.TYPE_USER)));
    }

    @RequestMapping(path = {"/followQuestion"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String followQuestion(@RequestParam("questionId") int questionId)
    {
        System.out.println("关注问题");
        User user = hostHolder.get();
        if(user==null)
        {
            return JSONUtils.getJSONString(999);
        }
        Question question = questionService.getQuestionById(questionId);
        boolean ret = followService.follow(user.getId(),questionId, EntityType.TYPE_QUESTION);
        eventProducer.fireEvent(new EventModel(EventType.FOLLOW).setActorId(user.getId())
            .setEntityId(questionId).setEntityType(EntityType.TYPE_QUESTION)
            .setExt("username",user.getName()).setEntityOwnerId(question.getUserId())
            .setExt("quetionName",question.getTitle()));
        Map<String, Object> info = new HashMap<>();
        info.put("headUrl", user.getHeadUrl());
        info.put("name", user.getName());
        info.put("id", user.getId());
        info.put("count", followService.getFollowerCount(EntityType.TYPE_QUESTION, questionId));
        return JSONUtils.getJSONString(ret ? 0 : 1, info);
    }

    @RequestMapping(path = {"/unfollowQuestion"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String unfollowQuestion(@RequestParam("questionId") int questionId)
    {
        User user = hostHolder.get();
        if(user==null)
        {
            return JSONUtils.getJSONString(999);
        }
        Question question = questionService.getQuestionById(questionId);
        boolean ret = followService.unfollow(user.getId(),questionId, EntityType.TYPE_QUESTION);
        eventProducer.fireEvent(new EventModel(EventType.UNFOLLOW).setActorId(user.getId())
                .setEntityId(questionId).setEntityType(EntityType.TYPE_QUESTION)
                .setExt("username",user.getName()).setEntityOwnerId(question.getUserId())
                .setExt("quetionName",question.getTitle()));
        Map<String, Object> info = new HashMap<>();
        info.put("id", user.getId());
        info.put("count", followService.getFollowerCount(EntityType.TYPE_QUESTION, questionId));
        return JSONUtils.getJSONString(ret ? 0 : 1, info);
    }

    @RequestMapping(path = "/user/{uid}/followees",method = RequestMethod.GET)
    public String getFollowees(@PathVariable("uid") int uid,
                               Model model)
    {
        List<Integer> followIds = followService.getFollowees(uid, EntityType.TYPE_USER, 0, 10);
        User user = hostHolder.get();
        if(user!=null)
        {
            model.addAttribute("followees",getUserInfo(user.getId(),followIds));
        }
        else{
            model.addAttribute("followees",getUserInfo(0,followIds));
        }
        model.addAttribute("followeeCount",followService.getFolloweeCount(uid,EntityType.TYPE_USER));
        model.addAttribute("curUser",userService.getUser(uid));
        return "followees";
    }

    @RequestMapping(path = "/user/{uid}/followers",method = RequestMethod.GET)
    public String getFollowers(@PathVariable("uid") int uid,
                               Model model)
    {
        List<Integer> followIds = followService.getFollowers(uid, EntityType.TYPE_USER, 0, 10);
        User user = hostHolder.get();
        if(user!=null)
        {
            model.addAttribute("followers",getUserInfo(user.getId(),followIds));
        }
        else{
            model.addAttribute("followers",getUserInfo(0,followIds));
        }
        model.addAttribute("followerCount",followService.getFollowerCount(uid,EntityType.TYPE_USER));
        model.addAttribute("curUser",userService.getUser(uid));
        return "followers";
    }


    private List<ViewObject> getUserInfo(int localUserId, List<Integer> userIds)
    {
        List<ViewObject> vos = new ArrayList<>();
        for(Integer id:userIds)
        {
            User user = userService.getUser(id);
            if(user==null)
            {
                continue;
            }
            ViewObject vo = new ViewObject();
            vo.set("user",user);
            vo.set("commentCount",commentService.getUserCommentCount(user.getId()));
            vo.set("followeeCount",followService.getFollowerCount(id,EntityType.TYPE_USER));
            vo.set("followerCount",followService.getFollowerCount(id,EntityType.TYPE_USER));
            if (localUserId != 0) {
                vo.set("followed", followService.isfollowers(localUserId, EntityType.TYPE_USER, id));
            } else {
                vo.set("followed", false);
            }
            vos.add(vo);
        }
        return vos;
    }

}
