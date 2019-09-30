package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-05-16
 */
@Data
public class SysAppVersion implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文件路径
     */
    private String sftPath;

    /**
     * 版本号
     */
    private String version;

    /**
     * 备注
     */
    private String remark;

    /**
     * 添加时间
     */
    private Date createDate;

    /**
     * 0删除，1正常
     */
    private Boolean delFlag;
    /**
     * 创建人
     */
    private Integer createBy;

    /**
     * 创建人
     */
    @TableField(exist = false)
    private String createUser;



}
