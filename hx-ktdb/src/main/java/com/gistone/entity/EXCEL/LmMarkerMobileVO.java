package com.gistone.entity.EXCEL;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class LmMarkerMobileVO {
    /**
     * id
     */
    private String id;


    /**
     * 界桩编号
     */
    @Excel(name = "预设界桩编号",height = 11, width = 15)
    private String jzNumber;

    /**
     * 界桩刻号
     */
    @Excel(name = "预设界桩刻号",height = 11, width = 15)
    private String jzKh;
    /**
     * 行政区划
     */

    private String code;
    /**
     * 是否为界桩 0不是，1是
     */
//    @TableField("is_bs")
//    private Integer isBs;

    /**
     * 界桩地貌
     */
    private String landform;

    /**
     * 校对经度
     */
    private String proofLon;

    /**
     * 校对纬度
     */
    private String proofLat;
    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;
    /**
     * 所在红线区
     */
    @Excel(name = "所在红线斑块",height = 11, width = 15)
    private String redlineName;

    /**
     * 所属红线名称
     */
    /*@Excel(name = "所属红线名称",height = 11, width = 15)
    private String redlineName;*/

    /**
     * 备注
     */
    private String remark;


    /**
     * 界桩位置缩略图
     */
    /*@Excel(name = "界桩位置缩略图",height = 11, width = 15)
    private String positionPicture;*/

    /**
     * 审核人
     */
    private String verifyPerson;

    /**
     * 审核时间
     */
    private Date verifyTime;

    /**
     * 数据入库时间
     */
    private String saveTime;


    /**
     * 创建人
     */
    /*@Excel(name = "创建人",height = 11, width = 15)
    private Integer createUser;*/
    /**
     * 创建人
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    /*@Excel(name = "更新时间",height = 11, width = 15)
    private Date updateTime;*/

    /**
     * 界桩现场图片
     */
    /*@Excel(name = "界桩现场图片",height = 11, width = 15)
    private List<LmMarkerPhoto> lmMarkerPhotos;*/


    /**
     * 界桩与方位物位置信息
     */
    /*@Excel(name = "界桩与方位物位置信息",height = 11, width = 15)
    private List<LmMarkerRelationPosition> lmMarkerRelationPositions;*/


    /**
     * 所属地方名
     */
    @Excel(name = "行政区划",height = 11, width = 15)
    private String placeName;





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
