package com.gistone.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.micrometer.core.lang.NonNull;
import lombok.Data;


import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 *
 * </p>
 *
 * @author xxh
 * @since 2019-01-22
 */
@TableName("sys_user")
@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NonNull
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NonNull
    @NotBlank(message = "密码不能为空")
    private String password;
    /**
     * 是否启用（默认1启用）
     */
    private Integer enable;

    /**
     * 用户类型（0pc端用户，1手机端用户）
     */
    private Integer type;
    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 创建人
     */
    private Integer createBy;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 更新人
     */
    private Integer updateBy;

    /**
     * 行政区划号
     */
    private String code;

    public SysUser() {

    }
    @TableField(exist = false)
    private Set<SysRole> roles;

    public SysUser(Integer id, @NonNull @NotBlank(message = "用户名不能为空") String username, @NonNull @NotBlank(message = "密码不能为空") String password, Integer enable, Integer type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enable = enable;
        this.type = type;
    }


    public String toMap() {
        return "{\"sysUser\":{\"id\":\"" + id + "\","
                + "\"username\":\"" + username + "\","
                + "\"password\":\"" + password + "\","
                + "\"enable\":\"" + enable + "\","
                + "}}";
    }
}