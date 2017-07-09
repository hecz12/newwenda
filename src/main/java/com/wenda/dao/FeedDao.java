package com.wenda.dao;

import com.wenda.model.Feed;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by 49540 on 2017/7/7.
 */
@Mapper
public interface FeedDao {
    String INSERT_FILED = " user_id,create_date,type,data ";
    String SELECT_FILED = " id,"+INSERT_FILED;
    String TABLE_NAME = " feed ";

    @Insert({"insert into ",TABLE_NAME," ( ",INSERT_FILED," ) ",
            " values (#{userId},#{createDate},#{type},#{data})"})
    int addFeed(Feed feed);

    @Select({"select ",SELECT_FILED," from ",TABLE_NAME," where id = #{id}"})
    Feed selectFeedById(int id);

    List<Feed> selectUserFeeds(@Param("maxId") int maxId,
                               @Param("userIds") List<Integer> userIds,
                               @Param("count") int count);

    @Select({"select ",SELECT_FILED," from ",TABLE_NAME," limit #{offset},#{limit}"})
    List<Feed> selectFeeds(@Param("offset") int offset,
                           @Param("limit") int limit);

}
