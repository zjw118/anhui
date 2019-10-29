package com.gistone.util;

import net.sf.json.JSONObject;

import java.net.URL;

public class LonLatToAddress {

	    public static String getAdd(String lon, String lat ){
	    	//lat 小  log  大
	        //参数解释: 纬度,经度 type 001 (100代表道路，010代表POI，001代表门址，111可以同时显示前三项) ,高德
	        //String urlString = "https://restapi.amap.com/v3/geocode/regeo?location="+lonlat[0]+","+lonlat[1]+"&key=52f8604d8813b42d378e67c95a91e956";
	        	//天地图

	        //String urlString = "http://api.tianditu.gov.cn/geocoder?postStr={%27lon%27:117.60078238878693,%27lat%27:39.372913662786324,%27ver%27:1}&type=geocode&tk=ff5e3fb7af7f87c353d8c0fc47b4826e";
	        String urlString = "http://api.tianditu.gov.cn/geocoder?postStr={'lon':"+LonLatUtil.duToLonLat(lon)+",'lat':"+LonLatUtil.duToLonLat(lat)+",'ver':1}&type=geocode&tk=ff5e3fb7af7f87c353d8c0fc47b4826e";
	        String res = "";
	        try {
	            URL url = new URL(urlString);
	            java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();
	            conn.setDoOutput(true);
	            conn.setRequestMethod("GET");
	            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(),"UTF-8"));
	            String line;
	           while ((line = in.readLine()) != null) {
	               res += line+"\n";
	         }
	            in.close();
	        } catch (Exception e) {
	            System.out.println("error in wapaction,and e is " + e.getMessage());
	        }
	        JSONObject jsonObject = JSONObject.fromObject(res);
	       // System.out.println(jsonObject);
	        //高德返回
	        //return JSONObject.fromObject(JSONObject.fromObject(jsonObject.get("regeocode")).get("addressComponent")).get("district").toString();
	        //	天地图
	        return JSONObject.fromObject(JSONObject.fromObject(jsonObject.get("result")).get("addressComponent")).get("province").toString();
	    }
	    public static void main(String[] args) {
	    	System.out.println(LonLatToAddress.getAdd("116°59′38.4864″", "39°59′59.208″"));
		}

}
