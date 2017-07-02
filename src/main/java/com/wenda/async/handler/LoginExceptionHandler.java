package com.wenda.async.handler;

import com.wenda.Utils.SendMail;
import com.wenda.async.EventHandler;
import com.wenda.async.EventModel;
import com.wenda.async.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 49540 on 2017/7/1.
 */
@Service
public class LoginExceptionHandler implements EventHandler {

    @Autowired
    SendMail sendMail;


    @Override
    public void dohandle(EventModel eventModel) {
//        Map<String,Object> map = new HashMap<>();
//        map.put("username",eventModel.getExt("username"));;
//        sendMail.sendWithHTMLTemplate(String.valueOf(eventModel.getExt("email")),
//                "欢迎登陆我们的问答网站",
//                "/mails/active.html",map);
}

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.LOGIN);
    }
}
