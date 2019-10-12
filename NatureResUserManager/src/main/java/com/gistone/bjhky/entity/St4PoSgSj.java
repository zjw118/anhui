package com.gistone.bjhky.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 单位和保护地关联表
 * </p>
 *
 * @author zhaojingwei
 * @since 2019-09-16
 */
public class St4PoSgSj implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 单位id
     */
    @TableField("SJ001")
    private Integer sj001;

    /**
     * 所属保护地ID
     */
    @TableField("SG001")
    private Integer sg001;


    public Integer getSj001() {
        return sj001;
    }

    public void setSj001(Integer sj001) {
        this.sj001 = sj001;
    }

    public Integer getSg001() {
        return sg001;
    }

    public void setSg001(Integer sg001) {
        this.sg001 = sg001;
    }

    @Override
    public String toString() {
        return "St4PoSgSj{" +
        "sj001=" + sj001 +
        ", sg001=" + sg001 +
        "}";
    }
}
