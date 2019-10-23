package com.gistone.entity;

import java.io.Serializable;

/**
 * <p>
 * 台账任务关联表
 * </p>
 *
 * @author zhaojingwei
 * @since 2019-10-23
 */
public class St4PoClCo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 台账表的主键
     */
    private Integer co001;

    /**
     * 任务表的主键
     */
    private Integer cl001;


    public Integer getCo001() {
        return co001;
    }

    public void setCo001(Integer co001) {
        this.co001 = co001;
    }

    public Integer getCl001() {
        return cl001;
    }

    public void setCl001(Integer cl001) {
        this.cl001 = cl001;
    }

    @Override
    public String toString() {
        return "St4PoClCo{" +
        "co001=" + co001 +
        ", cl001=" + cl001 +
        "}";
    }
}
