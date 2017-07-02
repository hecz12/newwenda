package com.wenda.async.handler;

import com.wenda.Utils.WendaUtils;
import com.wenda.async.EventHandler;
import com.wenda.async.EventModel;
import com.wenda.async.EventType;
import com.wenda.model.Message;
import com.wenda.model.User;
import com.wenda.service.MessageService;
import com.wenda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by 49540 on 2017/6/30.
 */
@Service
public class LikeHandler implements EventHandler {

    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;

    @Override
    public void dohandle(EventModel eventModel) {
        Message message = new Message();
        message.setFromId(WendaUtils.ADVICE_NAME);
        message.setCreateDate(new Date());
        message.setToId(eventModel.getEntityOwnerId());
        User user = userService.getUser(eventModel.getActorId());
        System.out.println("问题id"+eventModel.getExt("questionId"));
        message.setContent("用户"+user.getName()+
                "赞了你的评论,http://localhost:8080/question/"
                + eventModel.getExt("questionId"));
        message.setHasRead(0);
        System.out.println("进入了异步队列");
        messageService.addMessage(message);

    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.LIKE);
    }
}
