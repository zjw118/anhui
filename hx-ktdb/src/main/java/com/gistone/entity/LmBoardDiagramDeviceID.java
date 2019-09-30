package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zf1017@foxmail.com
 * @date 2019/9/11 0011 17:07
 * @description
 */
@TableName("lm_board_diagram_deviceid")
@Data
public class LmBoardDiagramDeviceID implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 唯一主键
     */
    @TableId(value = "lm_board_deviceid_id", type = IdType.AUTO)
    private Integer lm_marker_deviceid_id;
    /**
     * lmboard表id
     */
    @TableField("lmBoard_diagram_id")
    private String lmBoard_diagram_id;
    /**
     * 上传文件id
     */
    @TableField("id")
    private String id;
    /**
     * 手机deviceid
     */
    @TableField("device_id")
    private String device_id;
    /**
     * 批次id
     */
    @TableField("batch_id")
    private String batch_id;
    /**
     * 0,导入数据，1上传数据
     */
    private Integer type;
}
