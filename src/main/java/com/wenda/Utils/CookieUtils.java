package com.wenda.Utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 49540 on 2017/6/27.
 */
public class CookieUtils {
    public static Cookie getCookieByName(HttpServletRequest request,
                                         String cookieName)
    {
        if(cookieName==null)
        {
            return null;
        }
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies)
        {
            if(cookieName.equals(cookie.getName()))
            {
                return cookie;
            }
        }
        return null;
    }
}
