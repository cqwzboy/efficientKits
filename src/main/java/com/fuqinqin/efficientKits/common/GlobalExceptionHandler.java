package com.fuqinqin.efficientKits.common;

import com.fuqinqin.efficientKits.entity.common.Result;
import com.fuqinqin.efficientKits.exception.EfficientMailException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 全局异常处理
 * @author fuqinqin
 * @since 2018-10-08
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = EfficientMailException.class)
    public ModelAndView efficientMailExceptionHandler(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod,  EfficientMailException e) throws IOException {
        ModelAndView modelAndView = null;
        // 判断是API调用，还是页面调用
        boolean isApi = handlerMethod!=null &&
                (handlerMethod.hasMethodAnnotation(ResponseBody.class)
                        || handlerMethod.getBeanType().isAnnotationPresent(RestController.class));
        if(isApi){
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            String reason = new Result(e.getResultCode(), e.getResultCode().msg()+"["+e.getMessage()+"]").toString();
            writer.write(reason);
            writer.flush();
        }else{
            // TODO ModelAndView 返回页面
        }

        return modelAndView;
    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod,  Exception e) throws IOException {
        ModelAndView modelAndView = null;
        // 判断是API调用，还是页面调用
        boolean isApi = handlerMethod!=null &&
                (handlerMethod.hasMethodAnnotation(ResponseBody.class)
                        || handlerMethod.getBeanType().isAnnotationPresent(RestController.class));
        if(isApi){
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            String reason = null;
            if(e instanceof EfficientMailException){
                EfficientMailException efficientMailException = (EfficientMailException) e;
                reason = new Result(efficientMailException.getResultCode(), efficientMailException.getResultCode().msg()+"["+e.getMessage()+"]").toString();
            }
            writer.write(reason);
            writer.flush();
        }else{
            // TODO ModelAndView 返回页面
        }

        return modelAndView;
    }

}
