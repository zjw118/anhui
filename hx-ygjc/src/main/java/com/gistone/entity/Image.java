package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 影像数据表
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Image extends Model<Image> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableId(value = "name")
    private String name;
    @TableId(value = "url")
    private String url;
    @TableId(value = "shpurl")
    private String shpurl;
    @TableId(value = "create_by")
    private Integer createBy;
    @TableId(value = "create_date")
    private String createDate;
    @TableId(value = "update_by")
    private Integer updateBy;
    @TableId(value = "update_date")
    private Date updateDate;
    @TableId(value = "remark")
    private String remark;
    @TableId(value = "del_flag")
    private Integer delFlag;
    @TableId(value = "shp")
    private String shp;
    @TableId(value = "contrast_red")
    private String contrastRed;
    @TableId(value = "contrast_red2")
    private String contrastRed2;
    @TableId(value = "sign")
    private Integer sign;
    @TableId(value = "evaluation")
    private String evaluation;
    @TableId(value = "audit_date")
    private Date auditDate;
    @TableId(value = "country_border")
    private String countryBorder;
    @TableId(value = "audit_path")
    private String auditPath;
    @TableId(value = "audit_path2")
    private String auditPath2;
    @TableId(value = "area")
    private Double area;
    @TableId(value = "plaque_number")
    private Integer plaqueNumber;



    @TableField(exist = false)
    private List list;
    protected Serializable pkVal() {
        return this.id;
    }

}
