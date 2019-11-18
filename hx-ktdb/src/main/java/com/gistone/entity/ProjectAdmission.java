package com.gistone.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-05-05
 */
@Data
public class ProjectAdmission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 项目名称
     */
    @Excel(name = "项目名称",height = 11, width = 15)
    private String name;

    

    /**
     * 位置的形状
     */
    @Excel(name = "位置的形状",height = 11, width = 15)
    private String shape;

    /**
     * 缓冲区半径
     */
    @Excel(name = "缓冲区半径",height = 11, width = 15)
    private Integer radius;

    /**
     * 缓冲区面积
     */
    @Excel(name = "缓冲区面积",height = 11, width = 15)
    private Double bufferRange;

    /**
     * 项目坐标shape文件存储路径
     */
//    @Excel(name = "shp路径",height = 11, width = 15)
    private String coordinateUrl;
    /**
     * 项目描述
     */
    @Excel(name = "项目描述",height = 11, width = 15)
    private String description;

    /**
     * 分析结果
     */
    @Excel(name = "分析结果",height = 11, width = 15)
    private String result;

    /**
     * 创建人id
     */
    private Integer createBy;

    /**
     * 创建日期
     */

    private Date createDate;

    @TableField(exist = false)
    @Excel(name = "创建日期",height = 11, width = 15)
    private String createTime;

    /**
     * 修改人id
     */
    private Integer updateBy;

    /**
     * 修改日期
     */
    private Date updateDate;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 逻辑删除，0删除，1未删除
     */
    private Boolean delFlag;
    /**
     * word文件路径
     */
    @TableField(exist = true)
    private String fileUrl;

    /**
     * 分析明细
     */
    @TableField(exist = false)
    private List<AnalysisReport> analysisReports;

    /**
     * 缓冲区图形坐标
     */
    @TableField(exist = false)
    private List<Object> coordinate;
    @Excel(name = "项目类型",height = 11, width = 15)
    private String type;
    @Excel(name = "项目属性",height = 11, width = 15)
    private String attribute;

    private Date time;
    @TableField(exist = false)
    @Excel(name = "项目时间",height = 11, width = 15)
    private String timeString;




}
