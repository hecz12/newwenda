package com.wenda.dao;

import com.wenda.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;

/**
 * Created by 49540 on 2017/6/25.
 */
@Component
@Mapper
public interface UserDao {
    String TABLE_NAME = " user ";
    String FILED_NAME = " name,password,salt,head_url ";
    String SELECT_NAME = " id,"+FILED_NAME;

    @Insert({"insert into ",TABLE_NAME," (",FILED_NAME,") ","values" +
            " (#{name},#{password},#{salt},#{headUrl})" })
    int addUser(User user);

    @Update({"update ",TABLE_NAME," set password = #{password} where id = #{id}"})
    int updatePassword(User user);

    @Select({"select ",SELECT_NAME," from ",TABLE_NAME," where id = #{id}"})
    User selectById(int id);

    @Select({"select ",SELECT_NAME," from ",TABLE_NAME," where name = #{name}"})
    User selectByName(String name);

    @Delete({"delete from ",TABLE_NAME," where id = #{id}"})
    int deleteUser(User user);
}
