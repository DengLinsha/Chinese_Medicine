package com.me.common;

import lombok.Data;

@Data
public class Result<T> {
    // 操作代码
    private int code;
    // 提示信息
    private String msg;
    // 结果数据
    private T result;
    public Result(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    public Result(ResultEnum resultEnum, T result) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
        this.result = result;
    }

    public Result(String msg) {
        this.msg = msg;
    }

    // 成功返回封装-无数据
    public  static Result<String> success() {
        return new Result<String>(ResultEnum.SUCCESS);
    }

    // 成功返回封装-带数据
    public  static <T> Result<T> success(T result) {
        return new Result<T>(ResultEnum.SUCCESS, result);
    }

    // 失败返回封装-使用默认提示信息
    public  static Result<String> error() {
        return new Result<String>(ResultEnum.FAILURE);
    }

    // 失败返回封装-使用返回结果枚举提示信息
    public static  Result<String> error(ResultEnum resultEnum) {
        return new Result<String>(resultEnum);
    }

    // 失败返回封装-使用自定义提示信息
    public static  Result<String> error(String message) {
        return new Result<String>(message);
    }
}
