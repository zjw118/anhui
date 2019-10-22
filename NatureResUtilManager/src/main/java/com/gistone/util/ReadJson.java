package com.gistone.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadJson {



    public static JSONObject redaJson(String borderUrl, HttpServletRequest request) throws IOException {
        //拼接文件路径
        //String borderUrl = request.getServletContext().getRealPath("/") + "//" + url;
        //判断文件按是否存在
        File file = new File(borderUrl);
        JSONObject data = new JSONObject();
        if (file.exists()) {    //存在时
            //读取文件内容
            InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file), "utf-8");// 考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt;
            StringBuffer txt = new StringBuffer();
            while ((lineTxt = bufferedReader.readLine()) != null) {
                txt.append(lineTxt.replace(" ", ""));
            }
            String borderData = txt.toString();
            borderData = borderData.replace("保护区名称", "bhqName");
            borderData = borderData.replace("功能分区", "gnfq");

            if (borderData != null && !"".equals(borderData)) {
                //用阿里的fastjson包，加快数据转化速度
                data = com.alibaba.fastjson.JSON.parseObject(borderData);
            }
        }
        return data;
    }

    public static void main(String[] args) throws IOException {
        String array[] = {"101.63##36.6", "102.36##36.5"};
        String array2[] = {"108.63##38.6", "108.36##38.5"};
        List<String[]> data = new ArrayList<>();
        data.add(array);
        data.add(array2);
        writeGeoJson(data,"d://@/123.json");

    }

    public static String writeGeoJson(List<String[]> list, String path) throws IOException {
        //构造geojson格式数据
        String geoJson = "{'type':'FeatureCollection','features':[";
        JSONArray coordinatesList = new JSONArray();
        for(int i = 0 ; i < list.size() ; i++){
            String feature  = "{'type': 'Feature','propertie': {},'geometry': {'type': 'LineString','coordinates': ";
            String array[] = list.get(i);
            for (int j = 0; j < array.length; j++) {
                String details[] = array[j].split("##");
                coordinatesList.add(details);
            }
            feature += coordinatesList.toString();
            if(i == list.size() - 1){
                feature += "}}";
            }else{
                feature += "}},";
            }
            geoJson += feature;
        }

        geoJson += "]}";
        geoJson = geoJson.replaceAll("\"","");
        geoJson = geoJson.replaceAll("\'","\"");
        // 生成json格式文件
        try {
            // 保证创建一个新文件
            File file = new File(path);
            if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
                file.getParentFile().mkdirs();
            }
            if (file.exists()) { // 如果已存在,删除旧文件
                file.delete();
            }
            file.createNewFile();

            // 将格式化后的字符串写入文件
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(geoJson);
            write.flush();
            write.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

}
