package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 *
 *
 */
@TableName("lm_markerMobile_deviceid")
@Data
public class LmMarkerMobileDeviceID implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一主键
     */
    @TableId(value = "lm_markerMobile_deviceid_id", type = IdType.AUTO)
    private Integer lm_marker_deviceid_id;
    /**
     * ImmarkerMobile表_Id
     */
    @TableField("lmMobile_id")
    private String lm_id;
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
