package com.me.common;

import lombok.Getter;

@Getter
public enum ResultEnum {
    SUCCESS(200,"操作成功"),
    FAILURE(201,"操作失败"),
    /**系统相关的错误码：5开头**/
    ERROR(500, "系统异常，请稍后重试"),
    /**参数相关的错误码：1开头**/
    PARAM_ERROR(1000,"参数异常"),

    /**权限相关的错误码：2开头**/
    INVALID_TOKEN(2001,"访问令牌不合法"),
    ACCESS_DENIED(2002,"没有权限访问该资源"),
    USERNAME_OR_PASSWORD_ERROR(2003,"用户名或密码错误");

    private final int code;
    private final String msg;
    private ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
