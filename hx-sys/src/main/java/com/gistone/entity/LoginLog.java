package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author xjc
 * @since 2019-04-03
 */
@TableName("login_log")
@Data
public class LoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 登录人名称
     */
    @TableField("login_name")
    private String loginName;
    /**
     * 登录人id
     */
    @TableField("login_user_id")
    private Integer loginUserId;
    /**
     * 登录时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 0,pc端，1，移动端
     */
    private Integer type;
    /**
     * 登录IP
     */
    private String loginIp;

    /**
     * 0,登入；1，登出
     */
    private Integer loginType;



}
