package com.etc.hospital.exception;

import com.etc.hospital.util.Result;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 参数校验异常
    @ExceptionHandler(BindException.class)
    public Result<Void> handleParamException(BindException e) {
        String msg = e.getFieldError().getDefaultMessage();
        return Result.paramError(msg);
    }

    // 自定义业务异常
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        return Result.businessError(e.getMessage());
    }

    //【修改：捕获异常对象、打印日志、返回真实错误】
    @ExceptionHandler(Exception.class)
    public Result<Void> handleSysException(Exception e) {
        // 1.控制台打印详细报错（关键！看IDEA红色日志）
        e.printStackTrace();
        // 2.开发环境返回真实异常信息，方便定位bug
        return Result.systemError("系统异常："+e.getMessage());
    }
}