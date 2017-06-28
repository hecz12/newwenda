package com.wenda.service;

import com.wenda.Utils.MD5Utils;
import com.wenda.Utils.UUIDUtils;
import com.wenda.dao.LoginTicketDao;
import com.wenda.dao.UserDao;
import com.wenda.model.LoginTicket;
import com.wenda.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.Ticket;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by 49540 on 2017/6/26.
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    LoginTicketDao loginTicketDao;

    public String  addLoginTicket(int userId)
    {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date now = new Date();
        Date expired = new Date(3600*24*100+now.getTime());
        loginTicket.setExpired(expired);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUIDUtils.getUUIDName());
        loginTicketDao.addTicket(loginTicket);
        return loginTicket.getTicket();
    }

    public User getUser(int id)
    {
        return userDao.selectById(id);
    }


    public Map register(String name,String password)
    {
        Map<String,String> map = new HashMap<>();
        if(StringUtils.isBlank(name))
        {
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password))
        {
            map.put("msg","密码不能为空");
            return map;
        }
        if(userDao.selectByName(name)!=null)
        {
            map.put("msg","用户名已存在");
            return map;
        }
        User user = new User();
        user.setName(name);
        user.setSalt(UUIDUtils.getUUIDName());
        user.setPassword(MD5Utils.MD5(password+user.getSalt()));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://images.nowcoder.com/head/");
        stringBuilder.append(new Random().nextInt(1000));
        stringBuilder.append("t.png");
        user.setHeadUrl(stringBuilder.toString());
        userDao.addUser(user);
        if(user.getId()==0)
        {
            System.out.println("用户id不存在");
        }
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setStatus(0);
        loginTicket.setUserId(user.getId());
        loginTicketDao.updateTicketByUserId(loginTicket);
        map.put("ticket",addLoginTicket(user.getId()));
        return map;
    }

    public Map login(String name,String password)
    {
        Map<String,String> map = new HashMap<>();
        if(StringUtils.isBlank(name))
        {
            map.put("msg","用户名为空");
            return map;
        }
        if(StringUtils.isBlank(password))
        {
            map.put("msg","密码为空");
            return map;
        }
        User user = userDao.selectByName(name);
        if(user==null)
        {
            map.put("msg","此用户不存在");
            return map;
        }
        if(!user.getPassword().equals(MD5Utils.MD5(password+user.getSalt())))
        {
            map.put("msg","密码错误");
            return map;
        }
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setStatus(0);
        loginTicket.setUserId(user.getId());
        loginTicketDao.updateTicketByUserId(loginTicket);
        map.put("ticket",addLoginTicket(user.getId()));
        return map;
    }

    public void logout(String ticket) {
        LoginTicket loginTicket = loginTicketDao.selectLoginTicketByTicket(ticket);
        if(loginTicket!=null)
        {
            loginTicket.setStatus(1);
            loginTicketDao.updateTicket(loginTicket);
        }
    }
}
