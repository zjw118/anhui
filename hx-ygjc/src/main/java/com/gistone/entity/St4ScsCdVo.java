package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Data
public class St4ScsCdVo{
    private Integer CD001;
    private String CD002;
    private String CD003;
    private String CD004;
    private Integer SG001;
    private Integer CL001;
    private Integer CD007;
    private Integer SD001;
    private Integer CD009;
    private Integer CD010;
    private Date CD011;
    private String CD012;
    private String CD013;
    private String CD014;
    private Integer CD015;
    private Integer image_id;
    private String active_name;
    private String active_type;
    private String area;
    private String descri;
    private String geometry;
    private Integer group_id;
    private String CD016;
    private Integer CD017;
    private String region;
    private String position;
}
