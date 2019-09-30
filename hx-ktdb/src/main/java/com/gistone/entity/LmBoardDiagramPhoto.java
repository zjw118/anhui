package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-09-12
 */
@Data
public class LmBoardDiagramPhoto implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标识牌id
     */
    private Integer boardDiagramId;

    /**
     * 图片地址
     */
    private String url;

    /**
     * 图片类型，1.东，2南，3.西，4北,5.中间位置，6.缩略图
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 照片编号
     */
    private String number;



}
