package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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

    /**
     * 影像名称
     */
    private String name;

    /**
     * 服务地址
     */
    private String url;



    /**
     * 创建人id
     */
    private Integer createBy;

    /**
     * 创建日期
     */
    private LocalDateTime createDate;

    /**
     * 修改人id
     */
    private Integer updateBy;

    /**
     * 修改日期
     */
    private LocalDateTime updateDate;

    /**
     * 备注
     */
    private String remark;

    private Integer delFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
