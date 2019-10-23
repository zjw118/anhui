package com.gistone.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="基础类",description = "参数")
public class BaseEntity {
	@ApiModelProperty(name="pageNumber",value="页码，列表查询接口必传",dataType = "Integer",required = false,example = "1")
	@TableField(exist = false)
	private Integer pageNumber;

	@ApiModelProperty(name="pageSize",value="页容，列表查询接口必传",dataType = "Integer",required = false,example = "10")
	@TableField(exist = false)
	private Integer pageSize;

	//@JsonIgnore
	@ApiModelProperty(name="sortOrder",value="排序方式",dataType = "String",required = false,example = "列表接口必传，其他接口非必传")
	@TableField(exist = false)
	private String sortOrder;

	//@JsonIgnore
	@ApiModelProperty(name="sortName",value="排序字段",dataType = "String",required = false,example = "列表接口必传，其他接口非必传")
	@TableField(exist = false)
	private String sortName;
	@ApiModelProperty(name="strTime",value="时间范围段-起始时间",dataType = "String",required = false,example = "2019-08-15 19:20:20")
	@TableField(exist = false)
	private String strTime;
	@ApiModelProperty(name="endTime",value="时间范围段-结束时间",dataType = "String",required = false,example = "2019-08-15 19:20:20")
	@TableField(exist = false)
	private String endTime;

	@TableField(exist = false)
	private Integer firstLimit;

	//@JsonIgnore
	@ApiModelProperty(name="unionId",value="关联表主键",dataType = "Integer",required = false,example = "")
	@TableField(exist = false)
	private Integer unionId;
	@ApiModelProperty(name="isLimit",value="是否分页0是1否",dataType = "Integer",required = false,example = "")
	@TableField(exist = false)
	private Integer isLimit;


	@ApiModelProperty(name="uname",value="用户名，核查人",dataType = "String",required = false,example = "")
	@TableField(exist = false)
	private String uname;

	@ApiModelProperty(name="groupByName",value="分组字段",dataType = "String",required = false,example = "")
	@TableField(exist = false)
	private String groupByName;

	@ApiModelProperty(name="rUsername",value="人员名称",dataType = "String",required = false,example = "")
	@TableField(exist = false)
	private String rUsername;



}
