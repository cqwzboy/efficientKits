package com.fuqinqin.efficientKits.exception;

import com.fuqinqin.efficientKits.enums.ResultCode;
import lombok.Data;

/**
 * 发哦少年宫邮件异常
 * @author fuqinqin
 * @date 2018-09-28
 */
@Data
public class EfficientMailException extends RuntimeException {
    private ResultCode resultCode;

    public EfficientMailException(ResultCode resultCode) {
        super();
        this.resultCode = resultCode;
    }

    public EfficientMailException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

    public EfficientMailException(ResultCode resultCode, String message, Throwable cause) {
        super(message, cause);
        this.resultCode = resultCode;
    }

    public EfficientMailException(ResultCode resultCode, Throwable cause) {
        super(cause);
        this.resultCode = resultCode;
    }

}
