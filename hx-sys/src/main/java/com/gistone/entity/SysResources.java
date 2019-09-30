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
@TableName("sys_resources")
@Data
public class SysResources implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 资源名称
     */
    private String name;

    /**
     * 同name，字段，前端框架需要
     */
    @TableField(exist = false)
    private String title;
    /**
     * 资源url
     */
    private String resUrl;

    /**
     * apiUrl
     */
    private String apiUrl;
    /**
     * 资源类型   1:菜单    2：按钮
     */
    private Integer type;
    /**
     * 父资源
     */
    private Integer parentId;
    /**
     * 资源级别
     */
    private Integer level;
    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建人id
     */
    private Integer createBy;

    /**
     * 更新人id
     */
    private Integer updateBy;

    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 更新日期
     */
    private Date updateDate;
    /**
     * 逻辑删除 0，删除，1未删除
     */
    private Integer delFlag;

    /**
     * 子菜单
     */
    @TableField(exist = false)
    Set<SysResources> children;


}
