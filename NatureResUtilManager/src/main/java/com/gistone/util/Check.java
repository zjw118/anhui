package com.gistone.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 参数合法性验证
 * @author Administrator
 *
 */
public class Check {

	public static Boolean inputTextValue(String value){
		if(value!=null&&value!=""){
			value = value.toLowerCase();
		}
		
		String regEx1 = "(.*and.*)|(.*exec.*)|(.*count.*)|(.*chr.*)|(.*mid.*)|(.*master.*)|(.*or.*)|(.*truncate.*)|(.*char.*)|(.*declare.*)|(.*join.*)|"+
				"(.*&.*)|(.*;.*)|(.*[/$].*)|(.*%.*)|(.*@.*)|(.*\'.*)|(.*\".*)|(.*<.*)|(.*>.*)|(.*[/(].*)|(.*[/)].*)|(.*[/+].*)|(.*\r.*)|(.*\n.*)|(.*0x0d.*)|(.*0x0a.*)|(.*,.*)|(.*[/].*)|(.*\\|.*)|(.*\\\\.*)|"+
				"(.*insert.*)|(.*select.*)|(.*delete.*)|(.*update.*)|(.*create.*)|(.*drop.*)";
		
		Pattern pattern = Pattern.compile(regEx1);
		Matcher matcher = pattern.matcher(value);
		return matcher.find();
	}

}
