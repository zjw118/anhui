package com.gistone.VO;

import lombok.Data;

@Data
public class ResultVO<T> extends Object{

    /** 错误码. */
    private String code;

    /** 提示信息. */
    private String msg;

    /** 具体内容. */
    private T data;
}