package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 *
 */
@Data
public class TXunhuBiaozhuPho implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "pho_id", type = IdType.AUTO)
    private Long phoId;

    private String phoUrl;

    private Long phoBzId;

    public Long getPhoId() {
        return phoId;
    }

    public void setPhoId(Long phoId) {
        this.phoId = phoId;
    }

    public String getPhoUrl() {
        return phoUrl;
    }

    public void setPhoUrl(String phoUrl) {
        this.phoUrl = phoUrl == null ? null : phoUrl.trim();
    }

    public Long getPhoBzId() {
        return phoBzId;
    }

    public void setPhoBzId(Long phoBzId) {
        this.phoBzId = phoBzId;
    }
}