package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-05-21
 */
@Data
public class SysDbVersion implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * db文件的存储路径
     */
    private String dbUrl;

    /**
     * 版本
     */
    private String version;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 逻辑删除 0删除，1未删除
     */
    private Integer delFlag;

    /**
     * 创建人id
     */
    private Integer createBy;
    /**
     * 创建人
     */
    @TableField(exist = false)
    private String createUser;

}
