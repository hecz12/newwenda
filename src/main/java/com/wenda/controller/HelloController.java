package com.wenda.controller;

import com.wenda.Utils.MD5Utils;
import com.wenda.Utils.UUIDUtils;
import com.wenda.model.Question;
import com.wenda.model.User;
import com.wenda.model.ViewObject;
import com.wenda.service.QuestionService;
import com.wenda.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.lang.model.element.Name;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by 49540 on 2017/6/26.
 */
@Controller
public class HelloController {
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;

    private List<ViewObject> getQuestionList(int userId,int offset,int limit)
    {
        List<Question> questionList = questionService.getLatestQuestions(userId,offset,limit);
        List<ViewObject> vos = new ArrayList<>();
        for(Question question:questionList)
        {
            ViewObject vo = new ViewObject();
            vo.set("question",question);
            vo.set("user",userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String home(Model model)
    {
        List<ViewObject> vos = getQuestionList(0,0,10);
        model.addAttribute("vos",vos);
        return "index";
    }

    @RequestMapping(path = {"/user/{userId}"},method = {RequestMethod.GET,RequestMethod.POST})
    public String user(@PathVariable("userId") int userId, Model model)
    {
        List<ViewObject> vos = getQuestionList(userId,0,10);
        model.addAttribute("vos",vos);
        return "index";
    }

}


