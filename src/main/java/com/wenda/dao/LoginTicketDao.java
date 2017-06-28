package com.wenda.dao;

import com.wenda.model.LoginTicket;
import com.wenda.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import sun.security.krb5.internal.Ticket;

/**
 * Created by 49540 on 2017/6/27.
 */
@Component
@Mapper
public interface LoginTicketDao {
    String TABLE_NAME = " login_ticket ";
    String FILED_NAME = " user_id,ticket,expired,status ";
    String SELECT_FILED = "id, "+FILED_NAME;

    @Insert({"insert into ",TABLE_NAME," (" ,FILED_NAME," )" +
            " values (#{userId},#{ticket},#{expired},#{status})"})
    int addTicket(LoginTicket loginTicket);

    @Select({"select ",SELECT_FILED," from ",TABLE_NAME,"where ticket=#{ticket}"})
    LoginTicket selectLoginTicketByTicket(String ticket);

    @Update({"update ", TABLE_NAME, " set status=#{status} where ticket=#{ticket}"})
    void updateTicket(LoginTicket ticket);

    @Update({"update ", TABLE_NAME, " set status=#{status} where user_id=#{userId}"})
    void updateTicketByUserId(LoginTicket loginTicket);
}
