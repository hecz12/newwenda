package com.wenda.controller;

import com.wenda.Utils.JSONUtils;
import com.wenda.Utils.JedisAdapter;
import com.wenda.async.EventModel;
import com.wenda.async.EventProducer;
import com.wenda.async.EventType;
import com.wenda.model.Comment;
import com.wenda.model.EntityType;
import com.wenda.model.HostHolder;
import com.wenda.model.User;
import com.wenda.service.CommentService;
import com.wenda.service.LikeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 49540 on 2017/6/29.
 */
@Controller
public class LikeController {
    @Autowired
    LikeService likeService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    EventProducer producer;
    @Autowired
    CommentService commentService;

    @RequestMapping(path = {"/like"}, method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId)
    {
        User user = hostHolder.get();
        if(user==null)
        {
            return JSONUtils.getJSONString(999);
        }
        Comment comment = commentService.getComment(commentId);
        producer.fireEvent(new EventModel(EventType.LIKE).setActorId(user.getId())
                .setEntityId(comment.getId())
                .setEntityOwnerId(comment.getUserId())
                .setEntityType(comment.getEntityType())
                .setExt("questionId",String.valueOf(comment.getEntityId())));
        long likeCount = likeService.like(user.getId(),commentId, EntityType.TYPE_COMMENT);
        return JSONUtils.getJSONString(0,String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("commentId") int commentId)
    {
        User user = hostHolder.get();
        if(user==null)
        {
            return JSONUtils.getJSONString(999);
        }
        long likeCount = likeService.dislike(user.getId(),commentId, EntityType.TYPE_COMMENT);
        return JSONUtils.getJSONString(0,String.valueOf(likeCount));
    }
}
