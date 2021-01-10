package com.hy.controller;

import com.hy.CustomException.BusinessException;
import com.hy.CustomException.InUseException;
import com.hy.CustomException.SysException;
import com.hy.constant.MessageConstant;
import com.hy.entity.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;


/**
 * @author HY
 * @ClassName ExceptionController
 * @Description TODE
 * @DateTime 2021/1/7 15:45
 * Version 1.0
 */
@RestControllerAdvice
public class ExceptionController {


    @ExceptionHandler({InUseException.class})
    public Result handleInUseException(InUseException e) {
        return new Result(false,e.getMessage());
    }

    @ExceptionHandler({BusinessException.class})
    public Result handleBusinessException(BusinessException e) {
        return new Result(false,e.getMessage());
    }

    @ExceptionHandler({SysException.class})
    public Result handleSysException(SysException e) {
        return new Result(false,"系统异常");
    }

    @ExceptionHandler({IOException.class})
    public Result handleIOException(IOException e) {
        return new Result(false,e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        return new Result(false,"未知异常");
    }
}
