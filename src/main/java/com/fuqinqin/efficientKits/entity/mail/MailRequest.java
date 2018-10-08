package com.fuqinqin.efficientKits.entity.mail;

import com.fuqinqin.efficientKits.entity.common.BaseRequest;
import com.fuqinqin.efficientKits.enums.ResultCode;
import com.fuqinqin.efficientKits.exception.EfficientMailException;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
 * Mail请求实体类
 * @author fuqinqin
 * @date 2018-09-26
 */
@Data
@ToString
public class MailRequest implements BaseRequest {
    private String username;
    private String password;
    private String to;
    private String subject;
    private String text;

    @Override
    public void check() {
        if(StringUtils.isBlank(username)){
            throw new EfficientMailException(ResultCode.ILLEGAL_ARGUMENT, "username must not be empty");
        }
        if(StringUtils.isBlank(password)){
            throw new EfficientMailException(ResultCode.ILLEGAL_ARGUMENT, "password must not be empty");
        }
        if(StringUtils.isBlank(to)){
            throw new EfficientMailException(ResultCode.ILLEGAL_ARGUMENT, "to must not be empty");
        }
        if(StringUtils.isBlank(subject)){
            throw new EfficientMailException(ResultCode.ILLEGAL_ARGUMENT, "subject subject not be empty");
        }
        if(StringUtils.isBlank(text)){
            throw new EfficientMailException(ResultCode.ILLEGAL_ARGUMENT, "text subject not be empty");
        }
    }
}
