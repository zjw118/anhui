package com.gistone.bjhky.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * <p>
 *
 * </p>
 *
 * @author xxh
 * @since 2019-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4SysSg对象", description="")
public class St4SysSg extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "保护地数据唯一主键")
    @TableId(value = "SG001", type = IdType.AUTO)
    private Integer sg001;

    @ApiModelProperty(value = "创建人ID")
    @TableField("SG002")
    private Integer sg002;

    @ApiModelProperty(value = "创建时间")
    @TableField("SG003")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date sg003;

    @ApiModelProperty(value = "更新人ID")
    @TableField("SG004")
    private Integer sg004;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    @TableField("SG005")
    private Date sg005;

    @ApiModelProperty(value = "备注")
    @TableField("SG006")
    private String sg006;

    @ApiModelProperty(value = "逻辑删除 0是删除，1是未删除")
    @TableField("SG007")
    private Integer sg007;

    @ApiModelProperty(value = "保护地名称(添加必填、列表查询条件，表格展示)")
    @TableField("SG008")
    private String sg008;

    @ApiModelProperty(value = "保护地代码（添加必填、表格展示）")
    @TableField("SG009")
    private String sg009;

    @ApiModelProperty(value = "所属行政区划ID(添加必填、列表查询条件)")
    @TableField("SD001")
    private Integer sd001;

    @ApiModelProperty(value = "保护地面积（表格展示）")
    @TableField("SG011")
    private String sg011;

    @ApiModelProperty(value = "保护地类型ID(添加必填、列表查询条件)")
    @TableField("CA001")
    private Integer ca001;

    @ApiModelProperty(value = "保护地级别ID(添加必填、列表查询条件)")
    @TableField("CB001")
    private Integer cb001;

    @ApiModelProperty(value = "主要保护地对象（表格展示）")
    @TableField("SG013")
    private String sg013;

    @ApiModelProperty(value = "空间位置")
    @TableField("SG014")
    private String sg014;

    @ApiModelProperty(value = "中心点经度")
    @TableField("SG015")
    private String sg015;

    @ApiModelProperty(value = "中心点纬度")
    @TableField("SG016")
    private String sg016;

    @ApiModelProperty(value = "边界数据json格式")
    @TableField(exist = false)
    private String borderData;

    @ApiModelProperty(value = "保护地始建时间（表格展示）")
    @TableField("SG017")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date sg017;

    @ApiModelProperty(value = "主管部门（表格展示）")
    @TableField("SG018")
    private String sg018;

    @ApiModelProperty(value = "保护地地址")
    @TableField("SG019")
    private String sg019;

    @ApiModelProperty(value = "保护地类型（表格展示）")
    @TableField(exist = false)
    private St4ScsCa reserveType;

    @ApiModelProperty(value = "保护地级别（表格展示）")
    @TableField(exist = false)
    private St4ScsCb reserveRank;

    @ApiModelProperty(value = "保护地边界数据")
    @TableField(exist = false)
    private St4ScsCs reserveUrl;

    @ApiModelProperty(value = "保护地所属行政区划（表格展示）")
    @TableField(exist = false)
    private St4SysSd adminRegion;

    @ApiModelProperty(value = "点位信息")
    @TableField(exist = false)
    private List<T> children;


    @Ignore
    @ApiModelProperty(value = "权限用")
    @TableField(exist = false)
    private Integer unitId;

    @ApiModelProperty(value = "保护地ID集合,实时位置地图接口的多个保护区详情用",example = "[1,2,3]")
    @TableField(exist = false)
    private List<Integer> sgList;




}
