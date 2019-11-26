package com.gistone.entity.EXCEL;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.gistone.util.DateUtils;
import lombok.Data;

import java.util.Date;


@Data
public class LmPointVO {


//    @Excel(name = "id",height = 11, width = 15)
    private Integer lpId;
    /**
     * 编号
     */
    @Excel(name = "编号",height = 11, width = 15)
    private String lpCode;
    /**
     * 红线台账id
     */
//    @Excel(name = "红线台账id",height = 11, width = 15)
    private Integer lpSrldId;
    /**
     * 经度
     */
    @Excel(name = "经度",height = 11, width = 15)
    private String lpLon;
    /**
     * 纬度
     */
    @Excel(name = "纬度",height = 11, width = 15)
    private String lpLat;
//    @Excel(name = "x坐标",height = 11, width = 15)
//    private String lpX;
//    @Excel(name = "y坐标",height = 11, width = 15)
//    private String lpY;
    /**
     * 采集时间
     */
//    @Excel(name = "采集时间",height = 11, width = 15)
    private Date lpGettime;
    /**
     * 创建人
     */
//    @Excel(name = "添加人",height = 11, width = 15)
//    private String createUser;
//    @Excel(name = "添加时间",height = 11, width = 15)
    private String lpAddTime;

    /**
     * 修改人
     */
//    @Excel(name = "修改人",height = 11, width = 15)
//    private String updateUser;
//    @Excel(name = "修改时间",height = 11, width = 15)
    private String lpUpdTime;


    public Integer getLpId() {
        return lpId;
    }

    public void setLpId(Integer lpId) {
        this.lpId = lpId;
    }

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    public Integer getLpSrldId() {
        return lpSrldId;
    }

    public void setLpSrldId(Integer lpSrldId) {
        this.lpSrldId = lpSrldId;
    }

    public String getLpLon() {
        return lpLon;
    }

    public void setLpLon(Double lpLon) {
        this.lpLon = D2Dms(lpLon);
    }

    public String getLpLat() {
        return lpLat;
    }

    public void setLpLat(Double lpLat) {
        this.lpLat = D2Dms(lpLat);
    }


    public Date getLpGettime() {
        return lpGettime;
    }

    public void setLpGettime(Date lpGettime) {
        this.lpGettime = lpGettime;
    }



    public String getLpAddTime() {
        return lpAddTime;
    }

    public void setLpAddTime(Date lpAddTime) {
        this.lpAddTime = DateUtils.format(lpAddTime,"yyyy-MM-dd HH:mm:ss") ;
    }


    public String getLpUpdTime() {
        return lpUpdTime;
    }

    public void setLpUpdTime(Date lpUpdTime) {
        this.lpUpdTime =  DateUtils.format(lpUpdTime,"yyyy-MM-dd HH:mm:ss") ;
    }


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
