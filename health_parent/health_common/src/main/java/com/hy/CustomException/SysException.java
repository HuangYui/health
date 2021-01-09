package com.hy.CustomException;

/**
 * @author HY
 * @ClassName SysException
 * @Description TODE
 * @DateTime 2021/1/7 17:14
 * Version 1.0
 */
public class SysException extends RuntimeException {
    public SysException(String message) {
        super(message);
    }
}
