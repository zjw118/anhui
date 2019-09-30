package com.gistone.VO;

import lombok.Data;

/**
 * @author zf1017@foxmail.com
 * @date 2019/6/18 0018 16:48
 * @description
 */
@Data
public class PositionVO {
    private String distance;
    private String direction;
    private String ofReference;

    public PositionVO(String distance, String direction, String ofReference) {
        this.distance = distance;
        this.direction = direction;
        this.ofReference = ofReference;
    }
}
