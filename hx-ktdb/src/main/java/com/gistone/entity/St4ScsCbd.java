package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 移动端提交检测表
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-12-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class St4ScsCbd extends Model<St4ScsCbd> {

    private static final long serialVersionUID = 1L;

    /**
     * 移动端提交检测表主键
     */
    @TableId(value = "CBD001", type = IdType.AUTO)
    private Integer cbd001;

    /**
     * 操作人
     */
    @TableField("CBD002")
    private String cbd002;

    /**
     * 操作时间
     */
    @TableField("CBD003")
    private Date cbd003;

    /**
     * 问题斑块ID
     */
    @TableField("CBD004")
    private Integer cbd004;

    /**
     * 推送内容中间内容
     */
    @TableField("CBD005")
    private String cbd005;

    /**
     * 推送内容后缀内容
     */
    @TableField("CBD006")
    private String cbd006;

    /**
     * 0删除，1未删除
     */
    private Integer delFlag;

    private Integer verify;

    private String user;

    private Date createDate;

    @Override
    protected Serializable pkVal() {
        return this.cbd001;
    }

}
