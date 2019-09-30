package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

@Data
public class TXunhuBiaozhu {
    /**
     * 标注点的ID
     */
    private Integer bzId;
    /**
     * 标注点的巡护ID
     */
    private Integer bzXhid;
    /**
     * 路段id
     */
    private Integer bzXhlid;
    /**
     * 地点标注名称
     */
    private String bzDidian;
    /**
     * 天气
     */
    private String bzTianqi;
    /**
     * 生境
     */
    private String bzShengjing;
    /**
     * 标注点的海拔
     */
    private String bzHaiba;
    /**
     * 标注动物
     */
    private String bzDongwu;
    /**
     * 动物数量
     */
    private String bzDongwuShuliang;
    /**
     * 动物年龄
     */
    private String bzDongwuNianling;
    /**
     * 动物性别
     */
    private String bzDongwuXingbie;
    /**
     * 动物痕迹
     */
    private String bzDongwuHenji;
    /**
     * 动物备注
     */
    private String bzDongwuBeizhu;
    /**
     * 植物
     */
    private String bzZhiwu;
    /**
     * 植物备注
     */
    private String bzZhiwuBeizhu;
    /**
     * 标注人类
     */
    private String bzRenlei;
    /**
     * 人类备注
     */
    private String bzRenleiBeizhu;
    /**
     * 备注
     */
    private String bzBeizhu;
    /**
     * 经度
     */
    private String bzLng;
    /**
     * 纬度
     */
    private String bzLat;
    /**
     * 图片
     */
    private String bzTupian;
    /**
     * 录音
     */
    private String bzLuyin;
    /**
     * 条件
     */
    private String bzTijiao;
    /**
     * 植物数目
     */
    private String bzZhiwuNum;
    /**
     * 预览照片
     */
    private String bzPreviewImage;
    /**
     * 是否只有标注点
     */
    private Integer bzOnlyPoint;
    /**
     * 标注点的分组
     */
    private String bzOnlyPointGroup;
    /**
     * 巡护人员的id
     *
     */
    @TableField(exist = false)
    private  TQuestionVerification tQuestionVerification;
    private Integer bzXhryid;

    public List<TXunhuBiaozhuPho> getList() {
        return list;
    }

    public void setList(List<TXunhuBiaozhuPho> list) {
        this.list = list;
    }
    @TableField(exist = false)
    private List<TXunhuBiaozhuPho> list;




    //盐城湿地珍禽自然保护区
//    private String bzFengXiang;		//风向



//    private String bzFengLi;		//风力
//    private String bzWenDu;			//温度
//    private	String bzShiJian;		//记录时间
    

    public Integer getBzId() {
        return bzId;
    }

    public void setBzId(Integer bzId) {
        this.bzId = bzId;
    }

    public Integer getBzXhid() {
        return bzXhid;
    }

    public void setBzXhid(Integer bzXhid) {
        this.bzXhid = bzXhid;
    }

    public Integer getBzXhlid() {
        return bzXhlid;
    }

    public void setBzXhlid(Integer bzXhlid) {
        this.bzXhlid = bzXhlid;
    }

    public String getBzDidian() {
        return bzDidian;
    }

    public void setBzDidian(String bzDidian) {
        this.bzDidian = bzDidian;
    }

    public String getBzTianqi() {
        return bzTianqi;
    }

    public void setBzTianqi(String bzTianqi) {
        this.bzTianqi = bzTianqi;
    }

    public String getBzShengjing() {
        return bzShengjing;
    }

    public void setBzShengjing(String bzShengjing) {
        this.bzShengjing = bzShengjing;
    }

    public String getBzHaiba() {
        return bzHaiba;
    }

    public void setBzHaiba(String bzHaiba) {
        this.bzHaiba = bzHaiba;
    }

    public String getBzDongwu() {
        return bzDongwu;
    }

    public void setBzDongwu(String bzDongwu) {
        this.bzDongwu = bzDongwu;
    }

    public String getBzDongwuShuliang() {
        return bzDongwuShuliang;
    }

    public void setBzDongwuShuliang(String bzDongwuShuliang) {
        this.bzDongwuShuliang = bzDongwuShuliang;
    }

    public String getBzDongwuNianling() {
        return bzDongwuNianling;
    }

    public void setBzDongwuNianling(String bzDongwuNianling) {
        this.bzDongwuNianling = bzDongwuNianling;
    }

    public String getBzDongwuXingbie() {
        return bzDongwuXingbie;
    }

    public void setBzDongwuXingbie(String bzDongwuXingbie) {
        this.bzDongwuXingbie = bzDongwuXingbie;
    }

    public String getBzDongwuHenji() {
        return bzDongwuHenji;
    }

    public void setBzDongwuHenji(String bzDongwuHenji) {
        this.bzDongwuHenji = bzDongwuHenji;
    }

    public String getBzDongwuBeizhu() {
        return bzDongwuBeizhu;
    }

    public void setBzDongwuBeizhu(String bzDongwuBeizhu) {
        this.bzDongwuBeizhu = bzDongwuBeizhu;
    }

    public String getBzZhiwu() {
        return bzZhiwu;
    }

    public void setBzZhiwu(String bzZhiwu) {
        this.bzZhiwu = bzZhiwu;
    }

    public String getBzZhiwuBeizhu() {
        return bzZhiwuBeizhu;
    }

    public void setBzZhiwuBeizhu(String bzZhiwuBeizhu) {
        this.bzZhiwuBeizhu = bzZhiwuBeizhu;
    }

    public String getBzRenlei() {
        return bzRenlei;
    }

    public void setBzRenlei(String bzRenlei) {
        this.bzRenlei = bzRenlei;
    }

    public String getBzRenleiBeizhu() {
        return bzRenleiBeizhu;
    }

    public void setBzRenleiBeizhu(String bzRenleiBeizhu) {
        this.bzRenleiBeizhu = bzRenleiBeizhu;
    }

    public String getBzBeizhu() {
        return bzBeizhu;
    }

    public void setBzBeizhu(String bzBeizhu) {
        this.bzBeizhu = bzBeizhu;
    }

    public String getBzLng() {
        return bzLng;
    }

    public void setBzLng(String bzLng) {
        this.bzLng = bzLng;
    }

    public String getBzLat() {
        return bzLat;
    }

    public void setBzLat(String bzLat) {
        this.bzLat = bzLat;
    }

    public String getBzTupian() {
        return bzTupian;
    }

    public void setBzTupian(String bzTupian) {
        this.bzTupian = bzTupian;
    }

    public String getBzLuyin() {
        return bzLuyin;
    }

    public void setBzLuyin(String bzLuyin) {
        this.bzLuyin = bzLuyin;
    }

    public String getBzTijiao() {
        return bzTijiao;
    }

    public void setBzTijiao(String bzTijiao) {
        this.bzTijiao = bzTijiao;
    }

    public String getBzZhiwuNum() {
        return bzZhiwuNum;
    }

    public void setBzZhiwuNum(String bzZhiwuNum) {
        this.bzZhiwuNum = bzZhiwuNum;
    }

	public String getBzPreviewImage() {
		return bzPreviewImage;
	}

	public void setBzPreviewImage(String bzPreviewImage) {
		this.bzPreviewImage = bzPreviewImage;
	}

	public String getBzOnlyPointGroup() {
		return bzOnlyPointGroup;
	}

	public void setBzOnlyPointGroup(String bzOnlyPointGroup) {
		this.bzOnlyPointGroup = bzOnlyPointGroup;
	}

	public Integer getBzOnlyPoint() {
		return bzOnlyPoint;
	}

	public void setBzOnlyPoint(Integer bzOnlyPoint) {
		this.bzOnlyPoint = bzOnlyPoint;
	}




	public Integer getBzXhryid() {
		return bzXhryid;
	}

	public void setBzXhryid(Integer bzXhryid) {
		this.bzXhryid = bzXhryid;
	}


//	public String getBzFengXiang() {
//		return bzFengXiang;
//	}
//
//	public void setBzFengXiang(String bzFengXiang) {
//		this.bzFengXiang = bzFengXiang;
//	}
//
//	public String getBzFengLi() {
//		return bzFengLi;
//	}
//
//	public void setBzFengLi(String bzFengLi) {
//		this.bzFengLi = bzFengLi;
//	}
//
//	public String getBzWenDu() {
//		return bzWenDu;
//	}
//
//	public void setBzWenDu(String bzWenDu) {
//		this.bzWenDu = bzWenDu;
//	}
//
//	public String getBzShiJian() {
//		return bzShiJian;
//	}
//
//	public void setBzShiJian(String bzShiJian) {
//		this.bzShiJian = bzShiJian;
//	}
}