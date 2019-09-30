package com.gistone.util;

import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonObjectUtil {

	 public static JSONObject filterNull(JSONObject jsonObj) {
	        Iterator<String> it = jsonObj.keys();
	        JSONObject json = new JSONObject();
	        Object obj = null;
	        String key = null;
	        while (it.hasNext()) {
	            key = it.next();
	            obj = jsonObj.get(key);
	            if (obj instanceof JSONObject) {
	                filterNull((JSONObject) obj);
	            }
	            if (obj instanceof JSONArray) {
	                JSONArray objArr = (JSONArray) obj;
	                for (int i = 0; i < objArr.size(); i++) {
	                    filterNull(objArr.getJSONObject(i));
	                }
	            }
	            if (obj.equals("null") || obj == "null") {
	                json.put(key, null);
	            }else{
	            	json.put(key, obj);
	            }
//	            if (obj == null || obj instanceof JSONNull) {
//	                jsonObj.put(key, "");
//	            }
//	            if (obj.equals(null)) {
//	                jsonObj.put(key, "");
//	            }
	        }
	        return json;
	    }
}
