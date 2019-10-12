package com.gistone.bjhky.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ReadJsonFile {

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadJsonFile r = new ReadJsonFile();
		JSONObject str = r.getJsonObject();
	}*/
	/**
	 * 
	 *@description TODO 根据表名获取对应的表头数据
	 *@method  getTitleData
	 *@return  JSONArray
	 *@auto LuoShuai
	 *@date 2017年11月21日 下午1:44:00
	 */
	public JSONArray getTitleData(String table_name) {
        String path = getClass().getClassLoader().getResource("table_model.json").toString();
        JSONArray jsonArray = null;
        path = path.replace("\\", "/");
        if (path.contains(":")) {
        	path = path.replace("file:/","");
        }
        try {
            String input = FileUtils.readFileToString(new File(path), "UTF-8");
            JSONObject jsonObject = JSONObject.fromObject(input);
            if (jsonObject != null) {
                jsonArray = jsonObject.getJSONArray(table_name);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonArray = null;
        }
        return jsonArray;
    }
	
	/**
	 * 
	 *@description TODO  根据参数获取Json对象
	 *@method  getJsonObject
	 *@return  JSONObject
	 *@auto LuoShuai
	 *@date 2017年11月21日 下午1:44:21
	 */
	public JSONObject getJsonObject() {
        String path = getClass().getClassLoader().getResource("table_model.json").toString();
        JSONObject json = null;
        path = path.replace("\\", "/");
        if (path.contains(":")) {
        	path = path.replace("file:/","");
        }
        try {
            String input = FileUtils.readFileToString(new File(path), "UTF-8");
            json  = JSONObject.fromObject(input);
        } catch (Exception e) {
            e.printStackTrace();
            json = null;
        }
        return json;
    }

}
