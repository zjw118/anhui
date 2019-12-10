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

/**
 * <p>
 * 预设数据文件批次表
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ShpBatch extends Model<ShpBatch> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * shp文件存储路径
     */
    private String shpUrl;
    /**
     * 数据名称
     */
    private String name;

    private String ftpShpUrl;

    /**
     * gp服务地址
     */
    private String serviceUrl;

    /**
     * 1红线数据2界桩3标识牌4拐点
     */
    private Integer type;

    /**
     * 创建人id
     */
    private Integer createBy;

    /**
     * 创建日期批次
     */
    private LocalDateTime createDate;

    /**
     * 修改人id
     */
    private Integer updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateDate;


    private Integer count;

    /**
     * 备注
     */
    private String remark;

    private String grpoint;

    /**
     * 逻辑删除。0，删除，1是未删除
     */
    private Integer delFlag;


    /*
版本
     */
    private String version;

    /*
        存储空间数据json格式
     */
    private Integer jsonData;
    @ApiModelProperty(value = "边界数据表(江苏用安徽不用管)")
    @TableField(exist = false)
    List<St4ScsCbc> st4ScsCbcList;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
