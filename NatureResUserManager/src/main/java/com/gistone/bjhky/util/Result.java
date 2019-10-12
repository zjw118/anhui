package com.gistone.bjhky.util;

import java.util.List;

import net.sf.json.JSONObject;

/**
 * 结果响应结构
 */
public class Result {


	// 响应业务状态   返回码值为0代表返回结果正常，值大于0代表结果异常
	private Integer status;

	// 响应消息
	private String msg;

	// 响应中的数据
	private Object data;
	// 页数
	private Integer page;
	// 条数
	private Integer total;
	// 结果数组
	private List rows;
	
	private JSONObject obj;
	/**
	 * 执行正常调用该方法
	 * @param data 返回的数据
	 * @return 结果响应结构
	 */
	public static Result ok(Object data) {
		return new Result(data);
	}

	/**
	 * 执行正常调用该方法
	 * @return 结果响应结构
	 */
	public static Result ok() {
		return new Result(null);
	}

	/**
	 * 自定义状态码调用该方法
	 * @param status 自定义状态码
	 * @param msg 自定义信息
	 * @param data 自定义的数据
	 * @return 结果响应结构
	 */
	public static Result build(Integer status, String msg, Object data) {
		return new Result(status, msg, data);
	}

	/**
	 * 自定义状态码调用该方法
	 * @param status 自定义状态码
	 * @param msg 自定义信息
	 * @return 结果响应结构
	 */
	public static Result build(Integer status, String msg) {
		return new Result(status, msg, null);
	}
	public static Result build(List rows,Integer total, Integer page) {
		return new Result(rows, total, page);
	}
	public static Result build(JSONObject rows,Integer total, Integer page) {
		return new Result(rows, total, page);
	}

	
	
    /**
     * 有参构造
     * @param status 状态码
     * @param msg 信息
     * @param data 响应数据
     */
	public Result(Integer status, String msg, Object data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	/**
     * 有参构造
     * @param data 响应数据
     */
	public Result(Object data) {
		this.status = Status.SUCCESS; //默认返回正常
		this.msg = "success";
		this.data = data;
	}
	/**
	 * 有参构造
	 * @param data 响应数据
	 */
	public Result(Object data,String msg) {
		this.status = Status.SUCCESS; //默认返回正常
		this.msg = msg;
		this.data = data;
	}
	public Result(List rows,Integer total, Integer page) {
		this.rows = rows; //默认返回正常
		this.total = total;
		this.page = page;
	}
	/**
	 * 无参构造
	 */
	public Result() {}
	
	
	public Result(JSONObject rows2, Integer total2, Integer page2) {
		this.obj = rows2; //默认返回正常
		this.total = total2;
		this.page = page2;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	/**
	 * 以下是Getter And Setter 方法
	 */
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
