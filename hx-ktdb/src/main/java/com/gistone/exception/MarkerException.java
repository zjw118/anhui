package com.gistone.exception;

import com.gistone.util.ResultEnum;
import lombok.Data;

@Data
public class MarkerException extends RuntimeException {
    private String code;

    public MarkerException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());

        this.code = resultEnum.getCode();
    }

    public MarkerException(String code, String message) {
        super(message);
        this.code = code;
    }
    
}
