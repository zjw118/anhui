package com.gistone.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author zf1017@foxmail.com
 * @date 2019/10/29 0029 9:25
 * @description
 */
public class HttpRequestUtil {

    //处理http请求  requestUrl为请求地址  requestMethod请求方式，值为"GET"或"POST"
    public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
        StringBuffer buffer = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod(requestMethod);
            conn.connect();
            //往服务器端写内容 也就是发起http请求需要带的参数
            if (null != outputStr) {
                OutputStream os = conn.getOutputStream();
                os.write(outputStr.getBytes("utf-8"));
                os.close();
            }

            //读取服务器端返回的内容
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            buffer = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static void main(String[] args) {
        String s = httpRequest("http://115.29.42.107:6080/arcgis/rest/services/qhhx/hongxian/MapServer/0/query?returnGeometry=true&f=json&where=0%3D0&outFields=*", "GET", null);

        JSONObject parse = JSON.parseObject(s);
        JSONArray jsonArray = (JSONArray) parse.get("features");

        String url = PathUtile.getRandomPath("D:/epr/shp/", "x.shp");
        ShpUtil.importPreRedlinedata(jsonArray, url);
        System.out.println(jsonArray);
    }
}
