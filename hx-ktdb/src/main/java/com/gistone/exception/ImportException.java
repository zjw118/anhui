package com.gistone.exception;

import com.gistone.util.ResultEnum;
import lombok.Data;

/**
 * @author zf1017@foxmail.com
 * @date 2019/10/30 0030 10:02
 * @description
 */
@Data
public class ImportException extends RuntimeException {
    private String code;

    public ImportException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());

        this.code = resultEnum.getCode();
    }

    public ImportException(String code, String message) {
        super(message);
        this.code = code;
    }
}
