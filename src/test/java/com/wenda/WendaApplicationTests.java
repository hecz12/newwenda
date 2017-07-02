package com.wenda;

import com.wenda.Utils.SendMail;
import com.wenda.dao.LoginTicketDao;
import com.wenda.model.LoginTicket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.security.krb5.internal.Ticket;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WendaApplicationTests {
	@Autowired
	SendMail sendMail;
	@Test
	public void contextLoads() {
		Map<String,Object> map = new HashMap<>();
		map.put("username","何长治");
		sendMail.sendWithHTMLTemplate("495401146@qq.com","我是火车王",
				"/mails/active.html",map);
	}

}
