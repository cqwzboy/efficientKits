package com.fuqinqin.efficientKits.service;

import com.fuqinqin.efficientKits.entity.mail.Mail;
import com.fuqinqin.efficientKits.exception.EfficientMailException;

/**
 * 发送邮件服务
 * @author fuqinqin
 * @date 2018-09-26
 */
public interface IMailService {
   void sendMail(Mail mail) throws EfficientMailException;
}
