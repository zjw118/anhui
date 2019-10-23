package com.gistone.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
/*

// 	分页查询-获取总数量
	int getPoSum(PageBean pageBean);
//	分页查询-获取分页数据
	List<Object> selectPoList(PageBean pageBean);
	
	LIMIT #{firstLimit},#{pageSize}
	if(null==Integer.valueOf(pageBean.getPageIndex())||1>pageBean.getPageIndex()) {
		pageBean.setPageIndex(1);                   	 
	}	
	pageBean.setPageSize(10);

	PageBean pageBean = new PageBean();
	pageBean.setPageIndex(Integer.valueOf(map.get("pageIndex")+""));
	pageBean.setPageSize(Integer.valueOf(map.get("pageSize")+""));
	pageBean.setPoSum(verificationMapper.getPoSum(pageBean));
	pageBean.setPoList(verificationMapper.selectPoList(pageBean));
	return ResultVOUtil.success(pageBean);

*/
	
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
	public int getPoSum() {
		return poSum;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageSum() {
		return pageSum;
	}
	public void setPageSum(int pageSum) {
		this.pageSum = pageSum;
	}
	public int getFirstLimit() {
		return firstLimit;
	}
	public void setFirstLimit(int firstLimit) {
		this.firstLimit = firstLimit;
	}
	public List<Object> getPoList() {
		return poList;
	}
	public void setPoList(List<Object> poList) {
		this.poList = poList;
	}


}
