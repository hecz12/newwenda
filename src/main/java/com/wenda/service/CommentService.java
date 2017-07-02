package com.wenda.service;

import com.wenda.dao.CommentDao;
import com.wenda.model.Comment;
import org.apache.ibatis.javassist.bytecode.annotation.BooleanMemberValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 49540 on 2017/6/28.
 */
@Service
public class CommentService {
    @Autowired
    CommentDao commentDao;

    public List getCommentList(int entityId,int entityType,int limit,int offset)
    {
        return commentDao.selectCommentByEntity(entityId,entityType,limit,offset);
    }

    public int addComment(Comment comment)
    {
        comment.setCreateDate(new Date());
        return commentDao.addComment(comment)>0?comment.getId():0;
    }

    public Comment getComment(int id)
    {
        return commentDao.selectCommentById(id);
    }

    public int getCommentCount(int entityId,int entityType)
    {
        return commentDao.getCommentCount(entityId, entityType);
    }


    public void deleteComment(int entityId,int entityType)
    {
        commentDao.updateStatus(entityId,entityType,1);
    }
}
