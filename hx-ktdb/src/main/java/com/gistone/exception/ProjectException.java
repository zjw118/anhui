package com.gistone.exception;

import com.gistone.util.ResultEnum;

/**
 * @author zf1017@foxmail.com
 * @date 2019/5/8 0008 11:18
 * @description
 */
public class ProjectException extends RuntimeException {
    private String code;

    public ProjectException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());

        this.code = resultEnum.getCode();
    }

    public ProjectException(String code, String message) {
        super(message);
        this.code = code;
    }
}
