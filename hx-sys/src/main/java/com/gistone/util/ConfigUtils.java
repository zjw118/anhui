package com.gistone.util;

import lombok.Data;
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
@Data
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

	@Value("${APK_PATH}")
	private String APK_PATH;
	

}
