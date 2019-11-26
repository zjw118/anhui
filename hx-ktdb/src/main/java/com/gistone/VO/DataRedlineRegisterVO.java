package com.gistone.VO;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author zf1017@foxmail.com
 * @date 2019/11/26 0026 17:53
 * @description
 */
@Data
public class DataRedlineRegisterVO {
    @Excel(name = "红线编码",height = 11, width = 15)
    private String redlineCode;
    @Excel(name = "区域性红线",height = 11, width = 15)
    private String redlineName;
    @Excel(name = "生态植被",height = 11, width = 15)
    private String plant;
    @Excel(name = "面积",height = 11, width = 15)
    private String area;
}
