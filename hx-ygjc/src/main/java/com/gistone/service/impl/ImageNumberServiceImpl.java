package com.gistone.service.impl;

import com.gistone.VO.ResultVO;
import com.gistone.entity.ImageConfig;
import com.gistone.mapper.ImageConfigMapper;
import com.gistone.service.ImageConfigService;
import com.gistone.util.ResultVOUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ImageNumberServiceImpl implements ImageConfigService {


    @Autowired
    private ImageConfigMapper imageConfigMapper;

    @Override
    public ResultVO selectImageConfig() throws Exception{
        List<ImageConfig> list = imageConfigMapper.getImageConfig();
        JSONArray array = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            JSONObject classmap = new JSONObject();
            classmap.put("id", list.get(i).getId());
            classmap.put("parentId", list.get(i).getParentid());
            classmap.put("title", list.get(i).getName());
            classmap.put("children", "");
            int c = 0;
            array = inserts(array, classmap,c);
            if (flag == 0) {
                array.add(classmap);
            }
            flag = 0;
        }
        return ResultVOUtil.success(array);
    }


    //务必先按父节点正序排列 其次按主键排序  以主键顺序为结果
    private static Integer flag = 0;
    private JSONArray inserts(JSONArray array, JSONObject object,int c) throws Exception{
        c++;
        for (int j = 0; j < array.size(); j++) {
            JSONObject currObj = (JSONObject) array.get(j);
            //层数加项
            if(1==c){
//                currObj.put("expand", "true");
            }
            //判断如果是其子元素 则放入其children中
            if (object.get("parentId").equals(currObj.get("id"))) {
                flag = 1;
                if (currObj.get("children").equals("")) {
                    JSONArray array3 = new JSONArray();
                    array3.add(object);

                    currObj.put("children", array3);
                    array.set(j, currObj);
                } else {
                    JSONArray array2 = (JSONArray) currObj.get("children");
                    array2.add(object);

                    currObj.put("children", array2);
                    array.set(j, currObj);
                }
            }
        }
        for (int j = 0; j < array.size(); j++) {
            JSONObject currObj = (JSONObject) array.get(j);
            if (!currObj.get("children").equals("")) {
                JSONArray array4 = inserts((JSONArray) currObj.get("children"), object,c);

                currObj.put("children",array4);
                array.set(j, currObj);
            }
        }
        return array;
    }


}
