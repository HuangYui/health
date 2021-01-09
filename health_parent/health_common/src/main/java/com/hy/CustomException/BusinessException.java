package com.hy.CustomException;

/**
 * @author HY
 * @ClassName BusinessException
 * @Description TODE
 * @DateTime 2021/1/7 17:13
 * Version 1.0
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
