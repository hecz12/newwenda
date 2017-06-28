package com.wenda.dao;

import com.wenda.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by 49540 on 2017/6/26.
 */
@Component
@Mapper
public interface QuestionDao {
    String TABLE_NAME = " question ";
    String FILED_NAME = " title,content,user_id,create_date,comment_count ";
    String SELECT_FILED = " id,"+FILED_NAME;

    @Insert({"insert into ",TABLE_NAME,"( ",FILED_NAME,") " +
            "values (#{title},#{content},#{userId},#{createDate},#{commentCount})"})
    int addQuestion(Question question);

    @Select({"select ",SELECT_FILED," from ",TABLE_NAME," where id=#{id}"})
    Question selectQuestionById(int id);

    List<Question> selectLatestQuestions(@Param("userId") int userId,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);
}

