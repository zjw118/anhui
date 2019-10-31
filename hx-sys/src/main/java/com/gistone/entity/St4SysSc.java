package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户资源表
 * </p>
 *
 * @author lrz
 * @since 2019-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class St4SysSc extends Model<St4SysSc> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一主键
     */
    @TableId(value = "SC001", type = IdType.AUTO)
    private Integer sc001;

    /**
     * 模块名称
     */
    @TableField("SC002")
    private String sc002;

    /**
     * 父级id
     */
    @TableField("SC003")
    private Integer sc003;

    /**
     * 页面标签id
     */
    @TableField("SC004")
    private String sc004;

    /**
     * 更新时间
     */
    @TableField("SC005")
    private LocalDateTime sc005;

    /**
     * 备注
     */
    @TableField("SC006")
    private String sc006;

    /**
     * 逻辑删除 ，0是删除，1是未删除
     */
    @TableField("SC007")
    private Integer sc007;

    /**
     * 接口url
     */
    @TableField("SC010")
    private String sc010;

    /**
     * 资源类型  1.菜单 2.接口
     */
    @TableField("SC011")
    private Integer sc011;

    /**
     * 级别
     */
    @TableField("SC012")
    private Integer sc012;


    /**
     * 排序
     */
    @TableField("SC014")
    private Integer sc014;

    /**
     * 资源标志
     */
    @TableField("SC015")
    private String sc015;

    /**
     * 区分子父级
     */
    @TableField("SC016")
    private Integer sc016;

    /**
     * 区分资源所属系统
     */
    @TableField("SC017")
    private Integer sc017;

    /**
     * 子菜单
     */
    @TableField(exist = false)
    Set<St4SysSc> children;

    @TableField(exist = false)
    List<Integer> ids;

    @TableField(exist = false)
    private String parentName;

    @TableField(exist = false)
    private String title;

    @TableField(exist = false)
    private Integer value;

    @ApiModelProperty(value = "子菜单")
    @TableField(exist = false)
    private List<St4SysSc> childrens;

    @Override
    protected Serializable pkVal() {
        return this.sc001;
    }

}
