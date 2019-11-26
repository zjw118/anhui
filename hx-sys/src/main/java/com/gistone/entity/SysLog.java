package com.gistone.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-10-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class  SysLog extends Model<SysLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 请求类型
     */
    @Excel(name = "请求类型",height = 11, width = 15)
    private String type;

    /**
     * 日志标题
     */
    @Excel(name = "日志标题",height = 11, width = 15)
    private String title;

    /**
     * 操作IP地址
     */
    @Excel(name = "操作IP地址",height = 11, width = 15)
    private String remoteAddr;

    /**
     * 操作用户昵称
     */
//    @Excel(name = "操作用户昵称",height = 11, width = 15)
    private String username;

    /**
     * 请求URI
     */
    @Excel(name = "请求URI",height = 11, width = 15)
    private String requestUri;

    /**
     * 操作方式
     */
    @Excel(name = "操作方式",height = 11, width = 15)
    private String httpMethod;

    /**
     * 请求类型.方法
     */
    @Excel(name = "请求类型.方法",height = 11, width = 15)
    private String classMethod;

    /**
     * 操作提交的数据
     */
    @Excel(name = "操作提交的数据",height = 11, width = 15)
    private String params;

    /**
     * sessionId
     */
    private String sessionId;

    /**
     * 返回内容
     */
    @Excel(name = "返回内容",height = 11, width = 15)
    private String response;

    /**
     * 方法执行时间
     */
    @Excel(name = "方法执行时间",height = 11, width = 15)
    private Long useTime;

    /**
     * 浏览器信息
     */
    @Excel(name = "浏览器信息",height = 11, width = 15)
    private String browser;

    /**
     * 地区
     */
    private String area;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 网络服务提供商
     */
    private String isp;

    /**
     * 异常信息
     */
    @Excel(name = "异常信息",height = 11, width = 15)
    private String exception;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间",height = 11, width = 15)
    private Date createDate;

    private Long updateBy;

    private Date updateDate;

    private String remarks;

    private Boolean delFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
