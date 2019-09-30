package com.gistone.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xxh
 * @since 2019-01-22
 */
@TableName("sys_role_resources")
@Data
public class SysRoleResources implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer roleId;
    private Integer resourcesId;

    public SysRoleResources() {
    }

    public SysRoleResources(Integer roleId, Integer resourcesId) {
        this.roleId = roleId;
        this.resourcesId = resourcesId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getResourcesId() {
        return resourcesId;
    }

    public void setResourcesId(Integer resourcesId) {
        this.resourcesId = resourcesId;
    }

    @Override
    public String toString() {
        return "SysRoleResources{" +
        "roleId=" + roleId +
        ", resourcesId=" + resourcesId +
        "}";
    }
}
