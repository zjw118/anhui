package com.gistone.bjhky.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 航点巡护表单
 * </p>
 *
 * @author xxh
 * @since 2019-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="St4ScsCf对象", description="航点巡护表单")
public class St4ScsCf  extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "航点巡护表单主键")
    @TableId(value = "CF001", type = IdType.AUTO)
    private Integer cf001;

    @ApiModelProperty(value = "航点唯一标识")
    @TableField("CC002")
    private String cc002;

    @ApiModelProperty(value = "植物")
    @TableField("CF003")
    private String cf003;

    @ApiModelProperty(value = "植物备注")
    @TableField("CF004")
    private String cf004;

    @ApiModelProperty(value = "人类活动")
    @TableField("CF005")
    private String cf005;

    @ApiModelProperty(value = "人类活动备注")
    @TableField("CF006")
    private String cf006;

    @ApiModelProperty(value = "备注")
    @TableField("CF007")
    private String cf007;

    @ApiModelProperty(value = "经度")
    @TableField("CF008")
    private String cf008;

    @ApiModelProperty(value = "纬度")
    @TableField("CF009")
    private String cf009;

    @ApiModelProperty(value = "图片")
    @TableField("CF010")
    private String cf010;

    @ApiModelProperty(value = "录音")
    @TableField("CF011")
    private String cf011;

    @ApiModelProperty(value = "是否提交 0否 1是")
    @TableField("CF012")
    private Integer cf012;

    @ApiModelProperty(value = "植物数量")
    @TableField("CF013")
    private Integer cf013;

    @ApiModelProperty(value = "压缩后的预览图片")
    @TableField("CF014")
    private String cf014;

    @ApiModelProperty(value = "视频地址")
    @TableField("CF015")
    private String cf015;

    @ApiModelProperty(value = "动物")
    @TableField("CF016")
    private String cf016;

    @ApiModelProperty(value = "动物备注")
    @TableField("CF017")
    private String cf017;

    @ApiModelProperty(value = "动物数量")
    @TableField("CF018")
    private Integer cf018;

    @ApiModelProperty(value = "动物性别")
    @TableField("CF019")
    private String cf019;

    @ApiModelProperty(value = "动物痕迹")
    @TableField("CF020")
    private String cf020;

    @ApiModelProperty(value = "生境")
    @TableField("CF021")
    private String cf021;

    @ApiModelProperty(value = "天气")
    @TableField("CF022")
    private String cf022;

    @ApiModelProperty(value = "海拔")
    @TableField("CF023")
    private String cf023;

    @ApiModelProperty(value = "地点")
    @TableField("CF024")
    private String cf024;

    @ApiModelProperty(value = "动物年龄")
    @TableField("CF025")
    private String cf025;

    @ApiModelProperty(value = "0已删除 1未删除")
    @TableField("CF026")
    private Integer cf026;




}
