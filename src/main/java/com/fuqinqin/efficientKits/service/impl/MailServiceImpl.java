package com.fuqinqin.efficientKits.service.impl;

import com.fuqinqin.efficientKits.entity.mail.Mail;
import com.fuqinqin.efficientKits.enums.ResultCode;
import com.fuqinqin.efficientKits.exception.EfficientMailException;
import com.fuqinqin.efficientKits.service.IMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Properties;

/**
 * Created by fuqinqin on 2018/9/26.
 */
@Service
@Slf4j
public class MailServiceImpl implements IMailService {

    /**
     * 邮件发送
     * */
    @Override
    public void sendMail(Mail mail) throws EfficientMailException {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(mail.getSenderType().host());
        sender.setPort(mail.getSenderType().port());
        sender.setUsername(mail.getUsername());
        sender.setPassword(mail.getPassword());

        try {
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setFrom(mail.getUsername());
            mimeMessageHelper.setTo(mail.getTo());
            mimeMessageHelper.setSubject(mail.getSubject());
            mimeMessageHelper.setText(mail.getText(), true);
            Map<String, Resource> resourceMap = mail.getResourceMap();
            if(resourceMap!=null){
                int i = 1;
                for (String key : resourceMap.keySet()) {
                    mimeMessageHelper.addAttachment(key, resourceMap.get(key));
                }
            }

            // SSL
            final int PORT = 465;
            Properties javaMailProperties = new Properties();
            javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            javaMailProperties.setProperty("mail.smtp.socketFactory.port", PORT+"");
            javaMailProperties.setProperty("mail.smtp.port", PORT+"");
            sender.setJavaMailProperties(javaMailProperties);

            sender.send(mimeMessage);
            log.info("send mail to {} successfully", mail.getTo());
        } catch (Exception e){
            e.printStackTrace();
            if(e instanceof MailAuthenticationException){// 授权失败
                throw new EfficientMailException(ResultCode.MAIL_AUTH, e.getMessage());
            }
        }
    }
}
