package com.wenda.model;

import org.springframework.stereotype.Component;

/**
 * Created by 49540 on 2017/6/27.
 */
@Component
public class HostHolder {

    private static ThreadLocal<User> threadLocal = new ThreadLocal<>();

    public User get()
    {
        return threadLocal.get();
    }
    public void set(User user)
    {
        threadLocal.set(user);
    }
    public void clear()
    {
        threadLocal.remove();
    }
}
