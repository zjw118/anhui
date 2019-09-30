package com.gistone.handler;

import com.gistone.VO.ResultVO;
import com.gistone.exception.MarkerException;
import com.gistone.util.ResultVOUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常捕获
 */
@ControllerAdvice
public class MarkerExceptionHandler {

    @ExceptionHandler(value = MarkerException.class)
    @ResponseBody
    public ResultVO handlerSellerException(MarkerException e) {
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }

}
