package com.newhopemail.common.exception;

public enum BaseCode {
    UNKNOWN_EXCEPTION(10000,"系统未知异常"),
    VALID_EXCEPTION(10001,"数据校验异常");
    private final int code;
    private final String message;
    BaseCode(int code,String message){
        this.code=code;
        this.message=message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
