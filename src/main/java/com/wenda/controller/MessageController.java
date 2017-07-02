package com.wenda.controller;

import com.wenda.Utils.JSONUtils;
import com.wenda.dao.MessageDao;
import com.wenda.model.HostHolder;
import com.wenda.model.Message;
import com.wenda.model.User;
import com.wenda.model.ViewObject;
import com.wenda.service.MessageService;
import com.wenda.service.SensitiveService;
import com.wenda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceSupport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Created by 49540 on 2017/6/29.
 */
@Controller
public class MessageController {
    @Autowired
    MessageService messageService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserService userService;
    @Autowired
    SensitiveService sensitiveService;

    @PostMapping(path = "/msg/addMessage")
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName,
                             @RequestParam("content") String content)
    {
        Message message = new Message();
        User formUser = hostHolder.get();
        if(formUser==null)
        {
            return JSONUtils.getJSONString(999);
        }
        else{
            message.setFromId(formUser.getId());
        }
        User toUser = userService.getUserByName(toName);
        if(toUser==null)
        {
            return JSONUtils.getJSONString(1,"用户名不正确");
        }
        if(formUser.getId()==toUser.getId())
        {
            return JSONUtils.getJSONString(1,"不能向自己发送消息");
        }
        message.setToId(toUser.getId());
        message.setContent(HtmlUtils.htmlEscape(sensitiveService.filter(content)));
        if(messageService.addMessage(message)>0)
        {
            return JSONUtils.getJSONString(0);
        }
        return JSONUtils.getJSONString(1,"插入信息失败");
    }

    @GetMapping(path = "/msg/list")
    public String messageList(Model model)
    {
        User user = hostHolder.get();
        if(user==null)
        {
            return "redirect:/loginreg";
        }
        List<Message> messages = messageService.getConversationList(user.getId(),0,10);
        List vos = new ArrayList<ViewObject>();
        for(Message message:messages)
        {
            ViewObject vo = new ViewObject();
            vo.set("message",message);
            int targetId = user.getId()==message.getFromId()?message.getToId():message.getFromId();
            vo.set("user",userService.getUser(targetId));
            vo.set("unReadCount",messageService.getUnreadCount(user.getId(),message.getConversationId()));
            vos.add(vo);
        }
        model.addAttribute("vos",vos);
        return "letter";
    }

    @GetMapping(path = "/msg/detail")
    public String getConversationDetail(Model model,
                                @RequestParam(value = "conversationId") String conversationId)
    {
        System.out.println(conversationId);
        List<Message> messages = messageService.getConversationDetail(conversationId,0,10);
        List vos = new ArrayList<ViewObject>();
        User user = hostHolder.get();
        if(user==null)
        {
            return "redirect:/loginreg";
        }
        for(Message message:messages)
        {
            ViewObject vo = new ViewObject();
            vo.set("message",message);
            if(user.getId()==message.getFromId())
            {
                vo.set("status",0);
                vo.set("user",userService.getUser(user.getId()));
            }
            else{
                vo.set("status",1);
                vo.set("user",userService.getUser(message.getFromId()));
            }
            vos.add(vo);
        }
        model.addAttribute("vos",vos);
        messageService.updateReadState(user.getId(),conversationId);
        return "letterDetail";
    }
}
