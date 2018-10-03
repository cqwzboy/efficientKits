package com.fuqinqin.efficientKits.entity.mail;

import com.fuqinqin.efficientKits.enums.MailServiceSenderType;
import lombok.Data;
import org.springframework.core.io.Resource;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

/**
 * 邮件信息封装类
 * @author fuqinqin
 * @date 2018-09-26
 */
@Data
public class Mail {
    private String username;
    private String password;
    private MailServiceSenderType senderType;
    private String to;
    private String subject;
    private String text;
    private Map<String, Resource> resourceMap;

    public void decoder(String charset){
        try {
            this.username = URLDecoder.decode(this.username, charset);
            this.password = URLDecoder.decode(this.password, charset);
            this.to = URLDecoder.decode(this.to, charset);
            this.subject = URLDecoder.decode(this.subject, charset);
            this.text = URLDecoder.decode(this.text, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
