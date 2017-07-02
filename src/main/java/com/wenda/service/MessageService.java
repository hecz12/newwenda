package com.wenda.service;

import com.wenda.dao.MessageDao;
import com.wenda.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 49540 on 2017/6/29.
 */
@Service
public class MessageService {
    @Autowired
    MessageDao messageDao;


    public List<Message> getConversationDetail(String conversationId,int offset,int limit)
    {
        return messageDao.getConversationDetail(conversationId, offset,limit);
    }

    public int addMessage(Message message)
    {
        message.setCreateDate(new Date());
        message.setHasRead(0);
        return messageDao.addMessage(message)>0?message.getId():0;
    }

    public List getConversationList(int userId,int offset,int limit)
    {
        return messageDao.getConversationList(userId, offset, limit);
    }

    public int getUnreadCount(int userId,String conversationId)
    {
        return messageDao.getUnreadCount(userId,conversationId);
    }

    public void updateReadState(int userId,String conversationId)
    {
        messageDao.updateReadState(userId, conversationId);
    }
}
