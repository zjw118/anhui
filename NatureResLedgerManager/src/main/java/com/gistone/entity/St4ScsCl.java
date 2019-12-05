package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 任务批次表
 * </p>
 *
 * @author LiuXiong
 * @since 2019-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4ScsCl对象(没标记必传就是非必传)", description="任务批次表")
public class St4ScsCl extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="cl001",value="任务批次表主键",dataType = "Integer",required = true,example="1")
    @TableId(value = "CL001", type = IdType.AUTO)
    private Integer cl001;

    @ApiModelProperty(name="cl002",value="任务批次任务名称(必传)",dataType = "String",required = false,example="")
    @TableField(value ="CL002" ,fill = FieldFill.UPDATE)
    private String cl002;

    @ApiModelProperty(name="cl003",value="任务唯一标识",dataType = "String",required = false,example="")
    @TableField(value ="CL003",fill = FieldFill.UPDATE)
    private String cl003;

    @ApiModelProperty(name="cl004",value="任务类型(0巡护 1核查)",dataType = "Integer",required = false,example="")
    @TableField(value ="CL004",fill = FieldFill.UPDATE)
    private Integer cl004;



    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(name="cl005",value="任务结束时间")
    @TableField(value ="CL005",fill = FieldFill.UPDATE)
    private LocalDateTime cl005;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(name="cl006",value="任务开始时间")
    @TableField(value ="CL006",fill = FieldFill.UPDATE)
    private LocalDateTime cl006;

    @ApiModelProperty(name="cl007",value="任务坐标(参考路线)",dataType = "String",required = false,example="")
    @TableField(value ="CL007",fill = FieldFill.UPDATE)
    private String cl007;

    @ApiModelProperty(name="cl008",value="点位类型(1点 2线)",dataType = "String",required = false,example="")
    @TableField(value ="CL008",fill = FieldFill.UPDATE)
    private Integer cl008;

    @ApiModelProperty(name="cl009",value="任务描述",dataType = "String",required = false,example="")
    @TableField(value ="CL009",fill = FieldFill.UPDATE)
    private String cl009;

    @ApiModelProperty(name="cl010",value="批次年份",dataType = "String",required = false,example="")
    @TableField(value ="CL010",fill = FieldFill.UPDATE)
    private String cl010;

    @ApiModelProperty(name="cl011",value="1国家级  2市级",dataType = "String",required = false,example="")
    @TableField(value ="CL011",fill = FieldFill.UPDATE)
    private Integer cl011;

    @ApiModelProperty(name="cl012",value="0已删除 1未删除 默认未删除1",dataType = "String",required = false,example="")
    @TableField(value ="CL012")
    private Integer cl012;

    @ApiModelProperty(name="cl013",value="任务创建人ID",dataType = "String",required = false,example="")
    @TableField("CL013")
    private Integer cl013;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(name="cl014",value="任务创建时间",dataType = "String",required = false,example="")
    @TableField("CL014")
    private LocalDateTime cl014;

    @ApiModelProperty(name="cl015",value="任务创建形式 0 系统创建   1移动端创建",dataType = "String",required = false,example="")
    @TableField("CL015")
    private Integer cl015;

    @ApiModelProperty(name="cl016",value="所属行政区划代码",dataType = "String",required = false,example="")
    @TableField(value ="CL016",fill = FieldFill.UPDATE)
    private Integer cl016;

    @ApiModelProperty(name="uidList",value="绑定用户的id集合",dataType = "String",required = false,example="")
    @TableField(exist = false)
    private List<Integer> uidList;

    @ApiModelProperty(value = "台账Id集合（核查任务添加修改必传）", required = true,example="1")
    @TableField(exist = false)
    private List<Integer> ledgerIdList;
    @ApiModelProperty(value = "任务绑定的台账信息(返回的台账具体信息从这个里面取)", required = true,example="1")
    @TableField(exist = false)
    private  List<RlhdGroup> rlhdGroupList;
    @ApiModelProperty(value = "导出任务接口用传递任务list(其余接口不用关注)", required = true,example="1")
    @TableField(exist = false)
    private  List<Integer> clIds;

    @ApiModelProperty(name="type",value="权限用，不用管此字段",dataType = "String",required = false,example="")
    @TableField(exist = false)
    private RlhdGroup rlhdGroup;

    @ApiModelProperty(name="type",value="权限用，不用管此字段",dataType = "String",required = false,example="")
    @TableField(exist = false)
    private Integer type;
    @ApiModelProperty(name="type",value="导入辅助字段不用关注",dataType = "String",required = false,example="")
    @TableField(exist = false)
    private String ledgerId;

    @ApiModelProperty(name="adminRegionId",value="行政区ID",dataType = "String",required = false,example="")
    @TableField(exist = false)
    private String adminRegionId;
    @ApiModelProperty(name="adminRegionId",value="行政区",dataType = "String",required = false,example="")
    @TableField(exist = false)
    private SysCompany sysCompany;

    @ApiModelProperty(name="st4ScsCdList",value="任务下的问题斑块的信息",dataType = "String",required = false,example="")
    @TableField(exist = false)
    private List<St4ScsCd> st4ScsCdList;

    public static void main(String[] args) {
        String aa= "D:\\epr\\UploadData/dynamicLayerSpace//75822b253252477dbe15aa1c94603b2e_GF1B_PMS_E117.9_N31.3_20190907_L1A1227691115-PAN_ortho_fuse_clip.img";
        System.out.println(aa.substring(aa.lastIndexOf("/")+1 ));
    }



}
