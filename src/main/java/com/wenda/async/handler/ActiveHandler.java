package com.wenda.async.handler;

import com.wenda.Utils.SendMail;
import com.wenda.async.EventHandler;
import com.wenda.async.EventModel;
import com.wenda.async.EventType;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 49540 on 2017/7/2.
 */
@Service
public class ActiveHandler implements EventHandler {
    @Autowired
    SendMail sendMail;

    @Override
    public void dohandle(EventModel eventModel) {
        Map<String,Object> map = new HashMap<>();
        map.put("username",eventModel.getExt("username"));
        map.put("userId",eventModel.getActorId());
        map.put("active",eventModel.getExt("active"));
        System.out.println("进入了异步队列");
        sendMail.sendWithHTMLTemplate(String.valueOf(eventModel.getExt("email")),
                "欢迎注册我们的问答网站",
                "/mails/active.html",map);
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.Register);
    }
}
