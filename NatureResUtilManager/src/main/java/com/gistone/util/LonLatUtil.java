package com.gistone.util;

import java.text.DecimalFormat;

public class LonLatUtil {

	public static String duToLonLat(String val){
		Integer du = null;
		Integer fen = null;
		Double miao = null;
		 DecimalFormat df=new DecimalFormat("0.000000");//设置保留位数
		if(val.contains("°")){
			du = Integer.valueOf(val.split("°")[0]);
			fen = Integer.valueOf(val.split("°")[1].split("′")[0]);
			miao = Double.valueOf(val.split("°")[1].split("′")[1].split("″")[0]);
			return String.valueOf(df.format((float)Math.abs(du) + ((float)(Math.abs(fen))/60 + ((float)Math.abs(miao))/3600)));
		}else{
			return val;
		}
	}

}

