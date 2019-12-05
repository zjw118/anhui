package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-12-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Msgset extends Model<Msgset> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 发布时间
     */
    private Integer  time;

    /**
     * 审核标识
     */
    private Integer verify;

    /**
     * 发布对象id，存id集合
     */
    private String user;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
