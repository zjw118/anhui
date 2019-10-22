package com.gistone.entity;

public class BaseEntity {

	 private Integer pageNumber;
	    
	 private Integer pageSize;
	 
	 private String sortOrder;
	    
     private String sortName;
     
     private String rUsername;

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getrUsername() {
		return rUsername;
	}

	public void setrUsername(String rUsername) {
		this.rUsername = rUsername;
	}

}
