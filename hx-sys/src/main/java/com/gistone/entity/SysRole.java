package com.gistone.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 
 * </p>
 *
 * @author xxh
 * @since 2019-01-22
 */
@TableName("sys_role")
@Data
public class SysRole  implements Serializable  {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色名称
     */
    private String name;


    @TableField(exist = false)
    private Set<SysResources> resourcesSet;
    @TableField(exist = false)
    private List<Integer> resourceIds;


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
