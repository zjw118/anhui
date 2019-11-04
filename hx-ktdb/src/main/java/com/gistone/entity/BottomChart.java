package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 底图服务表
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-10-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BottomChart extends Model<BottomChart> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 底图服务名称
     */
    private String name;

    /**
     * 底图服务地址
     */
    private String url;

    /**
     * 创建人id
     */
    @TableField("createBy")
    private Integer createBy;

    private Integer type;

    /**
     * 创建日期
     */
    @TableField("createDate")
    private Date createDate;

    /**
     * 备注
     */
    private String remark;

    @TableField("updateBy")
    private Integer updateBy;

    @TableField("updateDate")
    private Date updateDate;

    /**
     * 创建时间
     */
    @TableField(exist = false)
    private String createTime;

    /**
     * 逻辑删除 0 删除，1 未删除
     */
    private Integer delFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
