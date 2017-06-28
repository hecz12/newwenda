package com.wenda.controller;

import com.wenda.Utils.JSONUtils;
import com.wenda.model.*;
import com.wenda.service.CommentService;
import com.wenda.service.QuestionService;
import com.wenda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by 49540 on 2017/6/28.
 */
@Controller
public class QuestionComtroller {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    @Autowired
    QuestionService questionService;
    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;

    @PostMapping(path = {"/question/add"})
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,
                              @RequestParam("content") String content) {
        try {
            Question question = new Question();
            question.setTitle(title);
            question.setContent(content);
            //敏感词过滤
            if (hostHolder.get() != null) {
                question.setUserId(hostHolder.get().getId());
            } else {
                return JSONUtils.getJSONString(999);
            }

            if (questionService.addQuestion(question) > 0) {
                return JSONUtils.getJSONString(0);
            }
        } catch (Exception e) {
            logger.warning("问题添加失败");
        }
        return JSONUtils.getJSONString(1,"失败");
    }

    @RequestMapping(path = {"/question/{questionId}"},method = RequestMethod.GET)
    public String questionDetail(@PathVariable(value = "questionId",required = false) int questionid
                                ,Model model)
    {
        Question question = questionService.getQuestionById(questionid);
        model.addAttribute("question",question);
        List<ViewObject> vos = new ArrayList<>();
        List<Comment> comments = commentService.getCommentList(question.getId(), EntityType.TYPE_QUESTION,10,0);
        for(Comment comment:comments)
        {
            ViewObject vo = new ViewObject();
            vo.set("comment",comment);
            vo.set("user",userService.getUser(comment.getUserId()));
            vos.add(vo);
        }
        model.addAttribute("vos", vos);
        return "detail";
    }
}
