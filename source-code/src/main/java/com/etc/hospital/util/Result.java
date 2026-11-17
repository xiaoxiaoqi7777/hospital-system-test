package com.etc.hospital.util;

import lombok.Data;

/**
 * 统一返回结果
 * 码值说明：
 * 200：正常成功
 * 400：参数校验异常
 * 401：业务异常（重复、状态错误、数据不存在等）
 * 500：系统未知异常
 */
@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    // 成功带数据
    public static <T> Result<T> success(T data) {
        Result<T> r = new Result<>();
        r.setCode(200);
        r.setMsg("操作成功");
        r.setData(data);
        return r;
    }
    // 成功不带数据
    public static <T> Result<T> success() {
        return success(null);
    }

    // 参数异常
    public static <T> Result<T> paramError(String msg) {
        Result<T> r = new Result<>();
        r.setCode(400);
        r.setMsg(msg);
        return r;
    }

    // 业务异常
    public static <T> Result<T> businessError(String msg) {
        Result<T> r = new Result<>();
        r.setCode(401);
        r.setMsg(msg);
        return r;
    }

    //【新增】系统异常：可传入错误详情
    public static <T> Result<T> systemError(String msg) {
        Result<T> r = new Result<>();
        r.setCode(500);
        r.setMsg(msg);
        return r;
    }
    // 默认系统异常
    public static <T> Result<T> systemError() {
        return systemError("系统内部异常，请稍后重试");
    }
}