package com.zzxx.exam.idorpwdexecption;

// 编号/密码错误异常
public class IdOrPwdException extends Exception{
    public IdOrPwdException() {
    }

    public IdOrPwdException(String message) {
        super(message);
    }

    public IdOrPwdException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdOrPwdException(Throwable cause) {
        super(cause);
    }

    public IdOrPwdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
