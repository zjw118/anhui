package com.gistone.entity.EXCEL;


import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
public class LmBoardVO{


//    /**
//     * 主键
//     */
//    @Excel(name = "id",height = 11, width = 15)
//    private Integer id;

    /**
     * 红线编码
     */
    @Excel(name = "红线编码",height = 11, width = 15)
    private String redlineName;



    /**
     * 行政区划编码
     */
//    @Excel(name = "行政区划编码",height = 11, width = 15)
    private String code;

    /**
     * 所在行政区划名称
     */
//    @Excel(name = "所在行政区划名称",height = 11, width = 15)
    private String placeName;

//    /**
//     * 关联红线id
//     */
//    @Excel(name = "关联红线id",height = 11, width = 15)
//    private Integer redlineId;


    /**
     * 标识牌内容
     */
//    @Excel(name = "标识牌内容",height = 11, width = 15)
    private String content;

    /**
     * 编号
     */
    @Excel(name = "编号",height = 11, width = 15)
    private String number;

    /**
     * 校正经度
     */
//    @Excel(name = "校正经度",height = 11, width = 15)
    private String proofLon;

    /**
     * 校正纬度
     */
//    @Excel(name = "校正纬度",height = 11, width = 15)
    private String proofLat;

    /**
     * 经度
     */
    @Excel(name = "经度",height = 11, width = 15)
    private String longitude;

    /**
     * 纬度
     */
    @Excel(name = "纬度",height = 11, width = 15)
    private String latitude;

    /**
     * 0预置数据，1采集数据
     */
//    @Excel(name = "记录类型",replace = {"预置数据_0", "采集数据_1"},height = 11, width = 15)
    private Integer type;

    /**
     * 标识牌树立时间
     */
//    @Excel(name = "标识牌树立时间",height = 11, width = 15)
    private String createDate;

    /**
     * 添加人
     */
/*    @Excel(name = "添加人",height = 11, width = 15)
    private Integer createBy;*/
    /**
     * 数据上传时间
     */
//    @Excel(name = "数据上传时间",height = 11, width = 15)
    private String saveDate;

    /**
     * 创建人
     */
//    @Excel(name = "创建人",height = 11, width = 15)
    private String createUser;

    /**
     * 修改人
     */
    /*@Excel(name = "修改人",height = 11, width = 15)
    private Integer updateBy;*/

    /**
     * 修改时间
     */
   /* @Excel(name = "修改时间",height = 11, width = 15)
    private String updateDate;*/

    /**
     * 逻辑删除 0是删除，1正常
     */
    private Integer delFlag;

    /**
     * 备注
     */
//    @Excel(name = "备注",height = 11, width = 15)
    private String remarks;

    /**
     * 标识牌现场图片
     */

   /* private List<LmBoardPhoto> lmBoardPhotos;*/
//    /**
////     * 红线名称
////     */
////    @Excel(name = "红线名称",height = 11, width = 15)
////    private String redlineName;



    /**
     * 审核人
     */
    /*@Excel(name = "审核人",height = 11, width = 15)
    private Integer verifyBy;




    *//**
     * 审核日期
     *//*
    @Excel(name = "审核日期",height = 11, width = 15)
    private Date verifyDate;*/

//    @Excel(name = "审核人",height = 11, width = 15)
    private String verifyPerson;

//    @Excel(name = "审核日期",height = 11, width = 15)
    private Date verifyDate;



    public  String D2Dms(Double d){
        String[] array=d.toString().split("[.]");
        String degrees=array[0];//得到度

        Double m=Double.parseDouble("0."+array[1])*60;
        String[] array1=m.toString().split("[.]");
        String minutes=array1[0];//得到分

        Double s=Double.parseDouble("0."+array1[1])*60;
        String seconds = String.format("%.2f",s);
        //String seconds=array2[0];//得到秒
        return degrees+"°"+minutes+"′"+seconds+"″";
    }
}
