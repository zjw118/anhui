package com.gistone.entity.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
@Data
public class CbaVo {



    @Excel(name = "编号",height = 11, width = 15)
    private String bms;

    @Excel(name = "省行政区",height = 11, width = 15)
    private String province;

    @Excel(name = "市行政区",height = 11, width = 15)
    private String city;

    @Excel(name = "行政区",height = 11, width = 15)
    private String dcounty;

    @Excel(name = "行政区代码",height = 11, width = 15)
    private String xzqhdm;

    @Excel(name = "要素代码",height = 11, width = 15)
    private String ysdm;

    @Excel(name = "主导生态系统服务功能",height = 11, width = 15)
    private String zdstxtfwgn;

    @Excel(name = "红线类型",height = 11, width = 15)
    private String hxlx;

    @Excel(name = "类型编码",height = 11, width = 15)
    private String lxbm;

    @Excel(name = "保护地名称",height = 11, width = 15)
    private String bhdmc;

    @Excel(name = "保护地级别",height = 11, width = 15)
    private String bhdjb;

    @Excel(name = "红线命名",height = 11, width = 15)
    private String hxmm;

    @Excel(name = "红线编码",height = 11, width = 15)
    private String hxbm;

    @Excel(name = "生态系统与植被类型",height = 11, width = 15)
    private String stxtyzblx;

    @Excel(name = "主要人为活动类型",height = 11, width = 15)
    private String zyrwhdlx;

    @Excel(name = "生态环境问题",height = 11, width = 15)
    private String sthjwt;

    @Excel(name = "管控措施",height = 11, width = 15)
    private String gkcs;

    @Excel(name = "图斑编码",height = 11, width = 15)
    private String tbbm;

    @Excel(name = "图斑面积",height = 11, width = 15)
    private Float tbmj;

    @Excel(name = "备注",height = 11, width = 15)
    private String bz;

    @Excel(name = "地理范围",height = 11, width = 15)
    private String dlfw;

    @Excel(name = "红线具体位置",height = 11, width = 15)
    private String hxjtwz;

    @Excel(name = "数据描述",height = 11, width = 15)
    private String sjms;

    @Excel(name = "功能分区",height = 11, width = 15)
    private String gnfq;

    @Excel(name = "分带",height = 11, width = 15)
    private String fd;

    @Excel(name = "一级流域",height = 11, width = 15)
    private String yjly;

    @Excel(name = "分类名称",height = 11, width = 15)
    private String flmc;

    @Excel(name = "保护对象",height = 11, width = 15)
    private String bhdx;

    @Excel(name = "保护目标",height = 11, width = 15)
    private String bhmb;

    @Excel(name = "管控要求",height = 11, width = 15)
    private String gkyq;


    @Excel(name = "现状",height = 11, width = 15)
    private String xz;

    @Excel(name = "要求",height = 11, width = 15)
    private String yq;

    @Excel(name = "水质现状",height = 11, width = 15)
    private String szxz;

    @Excel(name = "水质目标",height = 11, width = 15)
    private String szmb;


}
