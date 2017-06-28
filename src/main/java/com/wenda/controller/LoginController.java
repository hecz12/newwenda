package com.wenda.controller;

import com.sun.jmx.snmp.SnmpUnknownModelLcdException;
import com.wenda.Utils.StringUtils;
import com.wenda.dao.LoginTicketDao;
import com.wenda.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.file.Path;
import java.util.Map;

/**
 * Created by 49540 on 2017/6/27.
 */
@Controller
public class LoginController {
    @Autowired
    UserService userService;

    @GetMapping({"/loginreg"})
    public String loginReg(@RequestParam(value = "next",required = false) String next,
                           Model model)
    {
        model.addAttribute("next",next);
        return "login";
    }

    @RequestMapping(path = {"/reg"},method = {RequestMethod.POST})
    public String register(Model model,
                           @RequestParam("name") String name,
                           @RequestParam("password") String password,
                           @RequestParam("remember_me") boolean rememberme,
                           @RequestParam(value = "code",required = false) String code,
                           @RequestParam(value = "next",required = false) String next,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        try {
            Map map = userService.register(name, password);
            if (!map.containsKey("ticket")) {
                model.addAttribute("msg", map.get("msg"));
                model.addAttribute("next",next);
                System.out.print(map.get("msg"));
                return "login";
            }
            if(!checkCode(request,code))
            {
                model.addAttribute("msg","验证码错误");
                return "login";
            }
            Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
            cookie.setPath("/");
            if(rememberme)
            {
                cookie.setMaxAge(3600*24*5);
            }
            response.addCookie(cookie);
            if(StringUtils.isNotBlank(next))
            {
                return "redirect:"+next;
            }
            return "redirect:/";
        } catch (Exception e) {
            System.out.print("注册异常");
            model.addAttribute("msg", "服务器错误");
            return "login?next="+next;
        }
    }

    @RequestMapping(path = {"/login"},method = {RequestMethod.POST})
    public String login(Model model,
                           @RequestParam("name") String name,
                           @RequestParam("password") String password,
                           @RequestParam("remember_me") boolean rememberme,
                        @RequestParam(value = "code",required = false) String code,
                           @RequestParam(value = "next",required = false) String next,
                           HttpServletResponse response,
                            HttpServletRequest request)
    {
        Map map = userService.login(name,password);
        try {
            if(!map.containsKey("ticket"))
            {
                model.addAttribute("msg",map.get("msg"));
                model.addAttribute("next",next);
                return "login";
            }
            if(!checkCode(request,code))
            {
                model.addAttribute("msg","验证码错误");
                return "login";
            }
            Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
            cookie.setPath("/");
            if(rememberme)
            {
                cookie.setMaxAge(3600*24*5);
            }
            response.addCookie(cookie);
            if(StringUtils.isNotBlank(next))
            {
                return "redirect:"+next;
            }
            return "redirect:/";
        }
        catch (Exception e)
        {
            map.put("msg","服务器异常");
            return "login";
        }
    }

    @RequestMapping(path = {"/logout"},method = {RequestMethod.GET})
    public String logout(@CookieValue("ticket") String ticket)
    {
       userService.logout(ticket);
       return "redirect:/";
    }
    public boolean checkCode(HttpServletRequest request,String code)
    {
        HttpSession session = request.getSession();
        if(session!=null&&session.getAttribute("RANDOMVALIDATECODEKEY")!=null)
        {
            String value = String.valueOf(session.getAttribute("RANDOMVALIDATECODEKEY").toString());
            if(code.equalsIgnoreCase(value))
            {
                return true;
            }
        }
        return false;
    }
}
