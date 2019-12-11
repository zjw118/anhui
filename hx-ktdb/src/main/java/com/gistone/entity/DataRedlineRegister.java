package com.gistone.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author xjc
 * @since 2019-02-28
 */
@TableName("data_redline_register")
@Data
public class DataRedlineRegister extends Model<DataRedlineRegister> implements Serializable {

//    @TableId(value = "srld_id", type = IdType.AUTO)
    private static final long serialVersionUID = 1L;

    /**
     * 唯一主键
     */
    @TableId(value = "srld_id")
    //关联字段
    private Integer srldId;

    //主键
    @TableField(value = "srld_did")
    private Integer srldDid;
    /**
     * 市级行政区划
     */
    @TableField(value = "srld_city",exist = false)
    private String srldCity;
    /**
     * 县级行政区划
     */
    @TableField(value = "srld_country",exist = false)
    private String srldCountry;
    /**
     * 编码
     */
    @TableField("srld_code")
//    @Excel(name = "红线编码",height = 11, width = 15 ,orderNum = "0")
    private String srldCode; 
    /**
     * 名称
     */
    @TableField("srld_name")
    @Excel(name = "斑块名称",mergeRely={0},height = 11, width = 15)
    private String srldName;
    /**
     * 人口
     */
    @TableField("srld_population")
    private String srldPopulation;
    /**
     * 类型
     */
    @TableField("srld_type")
    @Excel(name = "生态功能属性",replace = { "生态功能重要区_01", "生态功能敏感区_03" },height = 11, width = 15)
    private String srldType;
    /**
     * 生态系统服务功能与保护目标
     */
    @TableField("srld_target")
    private String srldTarget;
    /**
     * 地理位置
     */
    @TableField("srld_position")
    private String srldPosition;
    /**
     * 区域面积
     */
    @TableField("srld_area")
    @Excel(name = "红线面积",height = 11, width = 15)
    private String srldArea;
    /**
     * 生态系统与植被类型
     */
    @TableField("srld_plant_type")
    @Excel(name = "植被类型",height = 11, width = 15)
    private String srldPlantType;
    /**
     * 主要人为活动
     */
    @TableField("srld_active")
    private String srldActive;
    /**
     * 生态环境问题
     */
    @TableField("srld_problem")
    private String srldProblem;
//    @Excel(name = "红线名称",height = 11, width = 15)
    @TableField("area_redline")
    private String areaRedline;
    /**
     * 管控措施
     */
    @TableField("srld_control")
    private String srldControl;
    /**
     * 添加时间
     */
    @TableField("srld_add_time")
    private Date srldAddTime;
    /**
     * 添加人
     */
    @TableField("srld_add_uid")
    private Integer srldAddUid;
    /**
     * 0正常1删除
     */
    @TableField("srld_is_del")
    private Integer srldIsDel;

    /**
     * 红线编号
     */
    @Excel(name = "红线编号",height = 11, width = 15)
    private String srldNumber;
    /**
     * 红线功能用途
     */
    private String target;
    /**
     * 红线审核数据id
     */
    @TableField("srld_shpBatch_id")
    private Integer srldShpBatchId;

}
