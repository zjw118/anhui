package com.gistone.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Configuration
@PropertySource(value = "classpath:/config.properties")
/**
 * 路径帮助类
 * @author WangShanxi
 */
public class ConfigUtil{
	@Value("${RunningStatus}")
	private String RunningStatus;

	@Value("${webAppName}")
	private String webAppName;


	public String getRunningStatus() {
		return RunningStatus;
	}

	public String getWebAppName() {
		return webAppName;
	}
	/*
	 * 当前系统的项目访问IP
	 */
	@Value("${ServerPath}")
	private String serverPath;

	public String getServerPath() {
		return serverPath;
	}
	//数据库ip
	@Value("${ServerIp}")
	private String serverIp;

	public String getServerIp() {
		return serverIp;
	}
	//数据库名称
	@Value("${DataBaseName}")
	private String dataBaseName;

	public String getDataBaseName() {
		return dataBaseName;
	}
	//数据库端口
	@Value("${DPort}")
	private String dPort;

	public String getDPort() {
		return dPort;
	}
	//数据库用户
	@Value("${DUserName}")
	private String dUserName;

	public String getDUserName() {
		return dUserName;
	}
	//数据库密码
	@Value("${DPassWord}")
	private String dPassWord;

	public String getDPassWord() {
		return dPassWord;
	}

	//shape文件上传路径
	@Value("${shapeFileUrl}")
	private String shapeFileUrl;

	public String getShapeFileUrl() {
		return shapeFileUrl;
	}
}
