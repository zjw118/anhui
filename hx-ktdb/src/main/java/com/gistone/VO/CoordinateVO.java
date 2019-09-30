package com.gistone.VO;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author zf1017@foxmail.com
 * @date 2019/5/6 0006 15:43
 * @description
 */
@Data
public class CoordinateVO {
    /**
     * 经度
     */
    @Excel(name = "经度", orderNum = "0")
    private Double longitude;
    /**
     * 纬度
     */
    @Excel(name = "纬度", orderNum = "1")
    private Double latitude;

    public CoordinateVO() {
    }

    public CoordinateVO(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
