package com.gistone.VO;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVO<T> extends Object implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 错误码. */
    private String code;

    /** 提示信息. */
    private String msg;

    /** 具体内容. */
    private T data;
}