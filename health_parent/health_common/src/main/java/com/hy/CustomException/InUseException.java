package com.hy.CustomException;

/**
 * @author HY
 * @ClassName InUseException
 * @Description TODE
 * @DateTime 2021/1/7 16:34
 * Version 1.0
 */
public class InUseException extends RuntimeException{

    public InUseException(String message) {
        super(message);
    }
}
