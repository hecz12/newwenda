package com.wenda.async.handler;

import com.wenda.Utils.WendaUtils;
import com.wenda.async.EventHandler;
import com.wenda.async.EventModel;
import com.wenda.async.EventType;
import com.wenda.model.EntityType;
import com.wenda.model.Message;
import com.wenda.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by 49540 on 2017/7/3.
 */
@Service
public class FollowHandler implements EventHandler {
    @Autowired
    MessageService messageService;

    @Override
    public void dohandle(EventModel eventModel) {
        Message message = new Message();
        message.setFromId(WendaUtils.ADVICE_NAME);
        message.setToId(eventModel.getEntityOwnerId());
        if(eventModel.getEntityType()== EntityType.TYPE_QUESTION) {
            message.setContent("恭喜您，" + eventModel.getExt("username") +
                        "关注了您的问题"+eventModel.getExt("quetionName")+
                        "，链接是:http://localhost:8080/quetion/"
                            +eventModel.getEntityId());
        }
        else{
            message.setContent("恭喜您，" + eventModel.getExt("username") +
                    "关注了您");
        }
        message.setHasRead(0);
        message.setCreateDate(new Date());
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.FOLLOW);
    }
}
