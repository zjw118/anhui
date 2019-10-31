package com.gistone.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

public class HttpUtil {
    /**
     * Get请求
     * @param url   请求地址  http://xxx/xxx?xx=x&xx=x
     * @param header   Header参数  map.put("Authorization","APPCODE 17be2aa3c1174b4194fb82b9589dbaa2");
     * @return
     * @throws Exception
     */
    public static String GET(String url,Map<String,String> header) throws Exception{
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            connection.setConnectTimeout(5000);  //连接超时时间 5秒
            connection.setReadTimeout(180000);   //读取数据时间 180秒
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) ...");
            if (header!=null) {
                Iterator<Map.Entry<String, String>> it =header.entrySet().iterator();
                while(it.hasNext()){
                    Map.Entry<String, String> entry = it.next();
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) result += line;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }
    }
    
    /**
     * POST请求
     * @param url   请求地址
     * @param param   请求参数 name1=value1&name2=value2
     * @param header   Header参数  map.put("Authorization","APPCODE 17be2aa3c1174b4194fb82b9589dbaa2");
     * @return
     */
    public static String POST(String url, String param,Map<String,String> header) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setConnectTimeout(5000);  //连接超时时间 5秒
            conn.setReadTimeout(15000);   //读取数据时间
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) ...");
            if (header!=null) {
                Iterator<Map.Entry<String, String>> it =header.entrySet().iterator();
                while(it.hasNext()){
                    Map.Entry<String, String> entry = it.next();
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try{
                if(out!=null) out.close();
                if(in!=null) in.close();
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }


}
