package com.gistone.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageBean implements Serializable{
	private static final long serialVersionUID = 1L;

	
	//注意数据顺序
	private int pageIndex;   	//当前页-手动填入 
	private int pageSize;  		//每页显示数量-手动填入 
	private int poSum;        	//条件数据总量 -查表  
	private int pageSum;   		//总页数-算出
	private int firstLimit;     //开始索引-算出
	private List<Object> poList;  //返回数据

	private String str1;

	public void setPoSum(int poSum) {
		setPageSum((poSum+pageSize-1)/pageSize); //总页数 =（总条数+每页条数-1）/每页条数
		setFirstLimit((pageIndex-1)*pageSize); //开始索引 = (当前页-1)*每页数量
		this.poSum = poSum;
	}


}
