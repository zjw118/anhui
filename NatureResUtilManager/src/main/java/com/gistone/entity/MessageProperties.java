package com.gistone.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("fileUpAndDownService")
//@ConfigurationProperties(prefix = "/application.properties")
public class MessageProperties {
    @Value("${fileSize}")
    private long fileSize;  //压缩大小
    @Value("${scaleRatio}")
    private double scaleRatio; //压缩比例
    @Value("${MDDIMG_LOCATION}")
    private String upPath; //保存路径
    @Value("${imageType}")
    private String imageType; //图片类型
    @Value("${nr_system}")
    private String nr_system;
    @Value("${nr_ledger}")
    private String nr_ledger;
    @Value("${nr_object}")
    private String nr_object;
    @Value("${nr_reserve}")
    private String nr_reserve;
    @Value("${nr_point}")
    private String nr_point;
    @Value("${nr_decode}")
    private String nr_decode;
    @Value("${nr_other}")
    private String nr_other;
    @Value("${nr_temp}")
    private String nr_temp;

    public String getNr_temp() {
        return nr_temp;
    }

    public void setNr_temp(String nr_temp) {
        this.nr_temp = nr_temp;
    }

    public String getNr_system() {
        return nr_system;
    }

    public void setNr_system(String nr_system) {
        this.nr_system = nr_system;
    }

    public String getNr_ledger() {
        return nr_ledger;
    }

    public void setNr_ledger(String nr_ledger) {
        this.nr_ledger = nr_ledger;
    }

    public String getNr_object() {
        return nr_object;
    }

    public void setNr_object(String nr_object) {
        this.nr_object = nr_object;
    }

    public String getNr_reserve() {
        return nr_reserve;
    }

    public void setNr_reserve(String nr_reserve) {
        this.nr_reserve = nr_reserve;
    }

    public String getNr_point() {
        return nr_point;
    }

    public void setNr_point(String nr_point) {
        this.nr_point = nr_point;
    }

    public String getNr_decode() {
        return nr_decode;
    }

    public void setNr_decode(String nr_decode) {
        this.nr_decode = nr_decode;
    }

    public String getNr_other() {
        return nr_other;
    }

    public void setNr_other(String nr_other) {
        this.nr_other = nr_other;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public double getScaleRatio() {
        return scaleRatio;
    }

    public void setScaleRatio(double scaleRatio) {
        this.scaleRatio = scaleRatio;
    }

    public String getUpPath() {
        return upPath;
    }

    public void setUpPath(String upPath) {
        this.upPath = upPath;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

}
