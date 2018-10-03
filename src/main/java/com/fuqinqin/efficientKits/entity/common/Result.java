package com.fuqinqin.efficientKits.entity.common;

import com.fuqinqin.efficientKits.enums.ResultCode;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 结果返回类
 * @author fuqinqin
 * @date 2018-09-26
 */
@Data
@ToString
public class Result<T> {
    private ResultCode resultCode;
    private String reason;
    private List<T> data;

    public Result(ResultCode resultCode){
        this.resultCode = resultCode;
        this.reason = resultCode.msg();
    }

    public Result(ResultCode resultCode, String reason){
        this.resultCode = resultCode;
        this.reason = reason;
    }
}
