package com.gistone.entity;

import lombok.Data;

import java.util.List;

@Data
public class CodeMenu {
    /**
     * id
     */
    private Integer id;

    /**
     * 名称
     */
    private String label;

    /**
     * 行政区划号
     */
    private String value;
    /**
     * 父id
     */
    private Integer comFpkid;
    /**
     * 子菜单
     */
    private List<CodeMenu> children;
}
