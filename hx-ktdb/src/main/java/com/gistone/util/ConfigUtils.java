package com.gistone.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 配置文件类
 * @author xjc
 *
 */
@Component
@Configuration
public class ConfigUtils {

	//导出路径
	@Value("${Excel_PATH}")
	private String Excel_PATH;

	//图片路径
	@Value("${PICTURE_PATH}")
	private String PICTURE_PATH;
	//Zip上传路径
	@Value("${ZIP_PATH}")
	private String ZIP_PATH;
	//Zip解压路径
	@Value("${ZIP_DECOM_PATH}")
	private String ZIP_DECOM_PATH;
	
	public String getExcel_PATH() {
		return Excel_PATH;
	}

	public void setExcel_PATH(String excel_PATH) {
		Excel_PATH = excel_PATH;
	}

	public String getPICTURE_PATH() {
		return PICTURE_PATH;
	}

	public void setPICTURE_PATH(String pICTURE_PATH) {
		PICTURE_PATH = pICTURE_PATH;
	}

	public String getZIP_PATH() {
		return ZIP_PATH;
	}

	public void setZIP_PATH(String ZIP_PATH) {
		this.ZIP_PATH = ZIP_PATH;
	}

	public String getZIP_DECOM_PATH() {
		return ZIP_DECOM_PATH;
	}

	public void setZIP_DECOM_PATH(String ZIP_DECOM_PATH) {
		this.ZIP_DECOM_PATH = ZIP_DECOM_PATH;
	}
}
