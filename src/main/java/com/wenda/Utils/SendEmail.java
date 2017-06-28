package com.wenda.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Created by 49540 on 2017/6/27.
 */
public class SendEmail {
    @Autowired
    JavaMailSenderImpl mailSender;

    static{

    }
}
