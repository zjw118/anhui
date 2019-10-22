package com.gistone.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class UrlsUtil {
	@Autowired
	private ConfigUtil configUtil;
	public String geturl() {
		String classFilePath = this.getClass()
				.getResource("UrlsUtil.class").toString();
		String urlFile = "";
		String status=configUtil.getRunningStatus();
		if (!status.equals("debug")) {
			
		
				urlFile = classFilePath.substring(6,
						classFilePath.indexOf(configUtil.getWebAppName())
								+ configUtil.getWebAppName().length())
						;
			
		} else {
				urlFile = classFilePath.substring(6,
						classFilePath.indexOf("classes/") + 8)
						+ "META-INF/resources/";
		}
		return urlFile;
	}
	
}
