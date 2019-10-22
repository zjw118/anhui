package com.gistone.util;

import net.sf.json.JSONObject;

import java.util.List;

/**
 * 结果响应结构
 */
public class ResultCp {


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
	public static ResultCp ok(Object data) {
		return new ResultCp(data);
	}

	/**
	 * 执行正常调用该方法
	 * @return 结果响应结构
	 */
	public static ResultCp ok() {
		return new ResultCp(null);
	}

	/**
	 * 自定义状态码调用该方法
	 * @param status 自定义状态码
	 * @param msg 自定义信息
	 * @param data 自定义的数据
	 * @return 结果响应结构
	 */
	public static ResultCp build(Integer status, String msg, Object data) {
		return new ResultCp(status, msg, data);
	}

	/**
	 * 自定义状态码调用该方法
	 * @param status 自定义状态码
	 * @param msg 自定义信息
	 * @return 结果响应结构
	 */
	public static ResultCp build(Integer status, String msg) {
		return new ResultCp(status, msg, null);
	}
	public static ResultCp build(List rows, Integer total, Integer page) {
		return new ResultCp(rows, total, page);
	}
	public static ResultCp build(JSONObject rows, Integer total, Integer page) {
		return new ResultCp(rows, total, page);
	}



    /**
     * 有参构造
     * @param status 状态码
     * @param msg 信息
     * @param data 响应数据
     */
	public ResultCp(Integer status, String msg, Object data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	/**
     * 有参构造
     * @param data 响应数据
     */
	public ResultCp(Object data) {
		this.status = Status.SUCCESS; //默认返回正常
		this.msg = "success";
		this.data = data;
	}
	/**
	 * 有参构造
	 * @param data 响应数据
	 */
	public ResultCp(Object data, String msg) {
		this.status = Status.SUCCESS; //默认返回正常
		this.msg = msg;
		this.data = data;
	}
	public ResultCp(List rows, Integer total, Integer page) {
		this.rows = rows; //默认返回正常
		this.total = total;
		this.page = page;
	}
	/**
	 * 无参构造
	 */
	public ResultCp() {}


	public ResultCp(JSONObject rows2, Integer total2, Integer page2) {
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
