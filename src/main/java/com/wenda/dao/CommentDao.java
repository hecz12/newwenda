package com.wenda.dao;

import com.wenda.model.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by 49540 on 2017/6/28.
 */
@Mapper
public interface CommentDao {
    String INSERT_FILED = " content,user_id,entity_id,entity_type,create_date,status ";
    String SELECT_FILED = " id,"+INSERT_FILED;
    String TABLE_NAME = "comment ";

    @Insert({"insert into",TABLE_NAME," (",INSERT_FILED," ) " +
            "values (#{content},#{userId},#{entityId},#{entityType},#{createDate}" +
            ",#{status})"})
    int addComment(Comment comment);

    @Select({"select ",SELECT_FILED," from ",TABLE_NAME," where entity_id=#{entityId}" +
            " and entity_type=#{entityType} order by create_date desc limit #{offset},#{limit}" })
    List<Comment> selectCommentByEntity(@Param("entityId") int entityId,
                               @Param("entityType") int entityType,
                               @Param("limit") int limit,
                               @Param("offset") int offset);

    @Select({"select ",SELECT_FILED," from ",TABLE_NAME," where id=#{id}"})
    Comment selectCommentById(@Param("id") int id);

    @Select({"select count(id) from ",TABLE_NAME," where entity_id=#{entityId} and " +
            " entity_type=#{entityType}"})
    int getCommentCount(@Param("entityId") int entityId,
                        @Param("entityType") int entityType);

    @Update({"update ",TABLE_NAME," set status=#{status} where" +
            " entity_id=#{entityId} and entity_type=#{entityType}"})
    int updateStatus(@Param("entityId") int entityId,
                     @Param("entityType") int entityType,
                     @Param("status") int status);

    @Select({"select count(id) from ",TABLE_NAME," where user_id = #{userId}"})
    int getUserCommentCount(int userId);
}
