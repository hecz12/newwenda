package com.wenda.dao;

import com.wenda.model.Message;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 49540 on 2017/6/29.
 */
@Mapper
public interface MessageDao {
    String TABLE_NAME = "message";
    String INSERT_FILED = " from_id,to_id,content,create_date" +
                            ",has_read,conversation_id ";
    String SELECT_FILED = " id,"+INSERT_FILED;
    @Insert({"insert into ",TABLE_NAME," ( ",INSERT_FILED," ) " +
            "values (#{fromId},#{toId},#{content},#{createDate}," +
            "#{hasRead},#{conversationId})"})
    int addMessage(Message message);

    @Select({"select ",SELECT_FILED," from ",TABLE_NAME," " +
            "where conversation_id = #{conversationId} order by create_date " +
            "desc limit #{offset},#{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId,
                                        @Param("offset") int offset,
                                        @Param("limit") int limit);
    //select *,count(id) id from (select * from message order by create_date desc) tt group by conversation_id order by create_date desc
    @Select({"select ",INSERT_FILED," ,count(id) as id from (select * from ",TABLE_NAME,"" +
            "where from_id = #{userId} or to_id = #{userId} order by create_date desc) tt " +
            " group by conversation_id order by create_date desc limit #{offset},#{limit}"})
    List<Message> getConversationList(@Param("userId") int userId,
                                      @Param("offset") int offset,
                                      @Param("limit") int limit);

    @Select({"select count(id) from ",TABLE_NAME," where to_id=#{userId} and " +
            "has_read = 0 and conversation_id=#{conversationId} "})
    int getUnreadCount(@Param("userId") int userId,
                       @Param("conversationId") String conversationId);

    @Update({"update ",TABLE_NAME," set has_read =1 where to_id=#{userId} " +
            " and conversation_id=#{conversationId} "})
    void updateReadState(@Param("userId") int userId,
                         @Param("conversationId") String conversationId);




}
