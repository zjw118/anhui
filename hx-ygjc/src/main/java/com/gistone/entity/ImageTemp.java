package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 影像数据表
 * </p>
 *
 * @author zjw
 * @since 2019-11-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ImageTemp对象", description="影像数据表")
public class ImageTemp extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "影像名称")
    private String name;

    @ApiModelProperty(value = "服务地址")
    private String url;

    @ApiModelProperty(value = "FTP服务器SHP存放地址")
    private String shpurl;

    @ApiModelProperty(value = "创建人id")
    private Integer createBy;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "手动输入的创建日期")
    private Date createDate;

    @ApiModelProperty(value = "修改人id")
    private Integer updateBy;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "自动生成的创建时间")
    private LocalDateTime updateDate;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "逻辑删除 0删除，1未删除")
    private Integer delFlag;

    @ApiModelProperty(value = "本地SHP存放地址")
    private String shp;

    @ApiModelProperty(value = "审核计算数据")
    private String contrastRed;

    @ApiModelProperty(value = "审核类型   1未审核、2审核通过")
    private Integer sign;

    @ApiModelProperty(value = "审核评语")
    private String evaluation;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "审核时间")
    private LocalDateTime auditDate;

    @ApiModelProperty(value = "四至(国家最大矩形的四点坐标)")
    private String countryBorder;

    @ApiModelProperty(value = "审核结果  json文件保存地址")
    private String auditPath;

    @ApiModelProperty(value = "总面积")
    private Double area;

    @ApiModelProperty(value = "斑块数量")
    private Integer plaqueNumber;

    @ApiModelProperty(value = "遥感影像数据URL")
    private String zipUrl;


}
