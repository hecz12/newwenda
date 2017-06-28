package com.wenda.service;


import com.wenda.dao.QuestionDao;
import com.wenda.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by 49540 on 2017/6/26.
 */
@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    @Autowired
    SensitiveService sensitiveService;

    public List<Question> getLatestQuestions(int userId, int offset, int limit)
    {
        return questionDao.selectLatestQuestions(userId, offset, limit);
    }

    public Question getQuestionById(int id)
    {
        return questionDao.selectQuestionById(id);
    }

    public int addQuestion(Question question)
    {
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));
        question.setCreateDate(new Date());
        question.setCommentCount(0);
        //敏感词过滤
        return questionDao.addQuestion(question)>0?question.getUserId():0;
    }

}
