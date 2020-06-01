package com.zoupanpan.www.base.webmvc;

import com.zoupanpan.www.base.bean.ResultBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * @author zoupanpan
 * @version 2020/5/31 12:01
 */
@ControllerAdvice
public class SimpleExceptionHandler {


    private static Logger logger = LoggerFactory.getLogger(SimpleExceptionHandler.class);

    @ExceptionHandler(value = SimpleException.class)
    @ResponseStatus
    @ResponseBody
    public Object handlerSimpleException(SimpleException exception) {
        logger.error(exception.getMessage(), exception);
        return ResultBean.FAILURE(exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void handlerException(Throwable throwable) {
        logger.error(throwable.getMessage(), throwable);
    }


}
