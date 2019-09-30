package com.gistone.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * <p>
 * 
 * </p>
 *
 * @author xjc
 * @since 2019-03-05
 */
@TableName("sys_mobile_user")
@Data
public class SysMobileUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * userID
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 电话号码
     */
    @TableField("phone_number")
    private String phoneNumber;
    /**
     * 真是姓名
     */
    @TableField("real_name")
    private String realName;
    /**
     * 行政区划
     */
    private String code;

    /**
     * 行政区划名称
     */
    @TableField(exist = false)
    private String codeName;
    /**
     * 部门id
     */
    @TableField("department_id")
    private Integer departmentId;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String department;

    /**
     * 人员类型
     */
    private String userType;

    /**
     * 所属单位明后才能
     */
    private String unitName;

    /**
     * 人员职责
     */
    private String duties;

    /**
     * 备注
     */
    private String remark;
    @TableField(exist = false)
    private Set<Integer> roleIds;

    @TableField(exist = false)
    private Set<SysRole> roles;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


}
