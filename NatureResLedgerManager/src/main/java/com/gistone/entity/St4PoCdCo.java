package com.gistone.entity;

import java.io.Serializable;

/**
 * <p>
 * 图斑台账关联表
 * </p>
 *
 * @author zhaojingwei
 * @since 2019-10-23
 */
public class St4PoCdCo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 图斑表的主键
     */
    private Integer cd001;

    /**
     * 台账表的主键
     */
    private Integer co001;


    public Integer getCd001() {
        return cd001;
    }

    public void setCd001(Integer cd001) {
        this.cd001 = cd001;
    }

    public Integer getCo001() {
        return co001;
    }

    public void setCo001(Integer co001) {
        this.co001 = co001;
    }

    @Override
    public String toString() {
        return "St4PoCdCo{" +
        "cd001=" + cd001 +
        ", co001=" + co001 +
        "}";
    }
}
