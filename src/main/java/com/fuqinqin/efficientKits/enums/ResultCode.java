package com.fuqinqin.efficientKits.enums;

/**
 * 返回码
 * @author fuqinqin
 * @date 2018-09-26
 */
public enum  ResultCode {
    OK(200, "success"),

    ILLEGAL_ARGUMENT(999, "非法参数"),

    /******************* Mail *****************/
    MAIL_FAILED(1001, "邮件发送失败"),
    MAIL_MAX_FILE_SIZE(1002, "单个附件超过10Mb"),
    SPECIAL_CHARACTERS(1003, "附件名称包含特殊字符"),
    MAIL_AUTH(1004, "授权失败，密码非登录免密，而是授权码，请仔细检查"),
    ILLEGAL_MAIL(1005, "非法的邮箱"),
    ;

    private Integer code;
    private String msg;

    ResultCode(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer code(){
        return this.code;
    }

    public String msg(){
        return this.msg;
    }
}
