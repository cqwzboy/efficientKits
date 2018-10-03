package com.fuqinqin.efficientKits.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 邮件服务器类型
 * String-邮件服务器发送地址
 * Integer-端口
 * @author fuqinqin
 * @date 2018-09-26
 */
public enum MailServiceSenderType {
    QQ("smtp.qq.com", 25),                          // QQ
    NTES_163("smtp.163.com", 25),                   // 网易163
    NTES_126("smtp.126.com", 25),                   // 网易126
    SINA("smtp.sina.com.cn", 25),                   // 新浪
    SOHU("smtp.sohu.com", 25),                      // 搜狐
    YAHOO("smtp.mail.yahoo.com", 25),               // 雅虎
    YAHOO_COM_CN("smtp.mail.yahoo.com.cn", 465),    // 雅虎 com.cn
    GMAIL("smtp.gmail.com", 25),                    // 谷歌
    CMCC_139("smtp.139.com", 25),                   // 中国移动
    ;

    private String host;
    private Integer port;

    MailServiceSenderType(String host, Integer port){
        this.host = host;
        this.port = port;
    }

    public String host(){return this.host;}

    public Integer port(){return this.port;}

    public boolean equalsTo(MailServiceSenderType senderType){
        if(senderType == null){
            return false;
        }

        if(this == senderType){
            return true;
        }

        if(this.name().equals(senderType.name()) && this.host.equals(senderType.host) && this.port.equals(senderType.port)){
            return true;
        }

        return false;
    }

    public static MailServiceSenderType parseUsername(String username){
        if(StringUtils.isBlank(username)){
            return null;
        }

        if(username.endsWith("qq.com")){
            return QQ;
        }else if(username.endsWith("163.com")){
            return NTES_163;
        }else if(username.endsWith("126.com")){
            return NTES_126;
        }else if(username.endsWith("sina.com.cn")){
            return SINA;
        }else if(username.endsWith("sohu.com")){
            return SOHU;
        }else if(username.endsWith("yahoo.com")){
            return YAHOO;
        }else if(username.endsWith("yahoo.com.cn")){
            return YAHOO_COM_CN;
        }else if(username.endsWith("gmail.com")){
            return GMAIL;
        }else if(username.endsWith("139.com")){
            return CMCC_139;
        }

        return null;
    }
}
