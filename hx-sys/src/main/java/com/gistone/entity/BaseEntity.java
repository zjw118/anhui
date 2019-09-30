package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * @author zf1017@foxmail.com
 * @date 2019/4/26 0026 13:28
 * @description
 */
@Data
public class BaseEntity {

    /**
     * 创建人id
     */
    private Integer createBy;

    /**
     * 创建人
     */
    @TableField(exist = false)
    private String createUser;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新人id
     */
    private Integer updateBy;

    /**
     * 更新人
     */
    @TableField(exist = false)
    private String updateUser;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 是否删除 0删除，1正常
     */
    private Integer delFlag;
}
