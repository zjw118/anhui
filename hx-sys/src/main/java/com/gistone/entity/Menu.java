package com.gistone.entity;

import lombok.Data;

import java.util.List;

@Data
public class Menu {

    /**
     * id
     */
    private Integer value;

    /**
     * 名称
     */
    private String label;

    /**
     * 行政区划号
     */
    private String comCode;
    /**
     * 父id
     */
    private Integer comFpkid;
    /**
     * 子菜单
     */
    private List<Menu> children;
}
