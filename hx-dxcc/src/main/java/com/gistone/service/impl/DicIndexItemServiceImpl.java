package com.gistone.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.VO.SurveyTableInfoVO;
import com.gistone.entity.DicFieldType;
import com.gistone.entity.DicIndexItem;
import com.gistone.mapper.DicFieldTypeMapper;
import com.gistone.mapper.DicIndexItemMapper;
import com.gistone.service.IDicIndexItemService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xjc
 * @since 2019-03-01
 */
@Service
@Transactional
@Slf4j
public class DicIndexItemServiceImpl extends ServiceImpl<DicIndexItemMapper, DicIndexItem> implements IDicIndexItemService {

    @Autowired
    private DicIndexItemMapper dicIndexItemMapper;

    @Autowired
    private DicFieldTypeMapper dicFieldTypeMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public List<Map<String, Object>> getAllList(DicIndexItem dit) {

        List<Map<String, Object>> list = dicIndexItemMapper.getAllList(dit);

        List<String> filedList = new ArrayList<String>();//存储字段主键id
        List<String> tableList = new ArrayList<String>();//存储表主键信息
        List<String> tableNameList = new ArrayList<String>();//存储表名称
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> infoList = list.get(i);
            filedList.add(infoList.get("pkId").toString());
        }
        filedList = duplicateRemovalString(filedList, new ArrayList<>());//去掉重复字段主键
        List<Map<String, Object>> listPkid = new ArrayList<>();//得到所有字段拼接
        List<Map<String, Object>> allList = new ArrayList<>();//得到所有

        for (int j = 0; j < filedList.size(); j++) {
            Map<String, Object> mapParam = new HashMap<>();//拼接字段和字段限制
            mapParam.put("pkid", filedList.get(j));
            JSONArray listFiledValue = new JSONArray();
            for (int i = 0; i < list.size(); i++) {//查询循环数据
                String filedPkId = list.get(i).get("pkId").toString();//字段主键
                String pkId = filedList.get(j);
                if (pkId.equals(filedPkId)) {
                    mapParam.put("index_item", list.get(i).get("index_item"));
                    mapParam.put("index_item_id", list.get(i).get("index_item_id"));
                    mapParam.put("filed_name", list.get(i).get("filed_name"));
                    mapParam.put("filed_type", list.get(i).get("filed_type"));
                    mapParam.put("is_must", list.get(i).get("is_must"));
                    //拼接fieldVlalue
                    Map<String, Object> mapFiledValue = new HashMap<>();
                    switch (list.get(i).get("filed_type").toString()) {
                        case "1":
                            if (list.get(i).get("string_length") != null) {
                                mapFiledValue.put("length", list.get(i).get("string_length"));
                            }
                            break;
                        case "2":
                            if (list.get(i).get("max") != null) {
                                mapFiledValue.put("max", list.get(i).get("max"));
                            }
                            if (list.get(i).get("min") != null) {
                                mapFiledValue.put("min", list.get(i).get("min"));
                            }
                            break;
                        case "3":
                            if (list.get(i).get("max") != null) {
                                mapFiledValue.put("max", list.get(i).get("max"));
                            }
                            if (list.get(i).get("min") != null) {
                                mapFiledValue.put("min", list.get(i).get("min"));
                            }
                            if (list.get(i).get("decimal_point") != null) {
                                mapFiledValue.put("decimal_point", list.get(i).get("decimal_point"));
                            }
                            break;
                        case "4":
                            if (list.get(i).get("single_select") != null) {
                                mapFiledValue.put("pk_id", list.get(i).get("pk_id"));
                                mapFiledValue.put("single_select", list.get(i).get("single_select"));
                            }
                            break;
                        case "5":
                            if (list.get(i).get("multiple_select") != null) {
                                mapFiledValue.put("pk_id", list.get(i).get("pk_id"));
                                mapFiledValue.put("multiple_select", list.get(i).get("multiple_select"));
                            }
                            break;
                        case "6":
                            if (list.get(i).get("rediao_type") != null) {
                                mapFiledValue.put("pk_id", list.get(i).get("pk_id"));
                                mapFiledValue.put("checked", list.get(i).get("rediao_type"));
                            }
                            break;
                        case "7":
                            if (list.get(i).get("time_limit") != null) {
                                mapFiledValue.put("time", list.get(i).get("time_limit"));
                            }
                            break;
                        default:
                            break;
                    }
                    if (mapFiledValue != null && mapFiledValue.size() > 0) {
                        JSONObject json = JSONObject.fromObject(mapFiledValue);
                        listFiledValue.add(json);
                    }
                }
            }
            if (listFiledValue != null && listFiledValue.size() > 0) {
                mapParam.put("filedValue", listFiledValue);
            }
            listPkid.add(mapParam);
        }

        for (int i = 0; i < listPkid.size(); i++) {
            tableList.add(listPkid.get(i).get("index_item_id").toString());
            tableNameList.add(listPkid.get(i).get("index_item").toString());
        }
        //去重
        tableList = duplicateRemovalString(tableList, new ArrayList<>());
//        tableNameList = duplicateRemovalString(tableNameList, new ArrayList<>());
        for (int i = 0; i < tableList.size(); i++) {
            Map<String, Object> objMap = new HashMap<>();//拼接表的数据
            List<Map<String, Object>> listFiledValue = new ArrayList<>();
            objMap.put("index_item_id", tableList.get(i));
            objMap.put("index_item", tableNameList.get(i));
            for (int j = 0; j < listPkid.size(); j++) {
                String filedPkId = listPkid.get(j).get("index_item_id").toString();//表主键
                String pkId = tableList.get(i);
                Map<String, Object> mapFiled = new HashMap<>();
                if (pkId.equals(filedPkId)) {
                    mapFiled.put("pkid", listPkid.get(j).get("pkid").toString());
                    mapFiled.put("filed_name", listPkid.get(j).get("filed_name").toString());
                    mapFiled.put("filed_type", listPkid.get(j).get("filed_type").toString());
                    mapFiled.put("is_must", listPkid.get(j).get("is_must").toString());
                    if (listPkid.get(j).get("filedValue") != null) {
                        mapFiled.put("filedValue", listPkid.get(j).get("filedValue").toString());
                    }

                    listFiledValue.add(mapFiled);
                }
            }
            objMap.put("field", listFiledValue);
            allList.add(objMap);
        }

        JSONArray json = JSONArray.fromObject(allList);
        redisTemplate.opsForValue().set("allList", json.toString());

        return allList;
    }

    /**
     * Description: 对List<String>进行去重
     *
     * @param StringList       需要被去重的List<String>
     * @param returnStringList 去重后的List<String>
     * @return List<String> 去重后的List<String>
     */
    private List<String> duplicateRemovalString(List<String> StringList, List<String> returnStringList) {
        if (StringList == null) {
            return StringList;
        }

        if (StringList.size() == 0) {
            return StringList;
        }

        Set<String> set = new HashSet<String>();
        for (String str : StringList) {
            if (!set.contains(str)) { // set中不包含重复的
                set.add(str);
                returnStringList.add(str);
            } else {
                continue;
            }
        }

        set.clear();
        return returnStringList;
    }

    //查询调查子表信息
    @Override
    public Map<String, Object> getSonTableList(Map<String, Object> resultMap) {

        //分页查询条件
        int number = 0;
        int size = 0;
        int page = 0;
        if (resultMap.get("pageSize") != null && !"".equals(resultMap.get("pageSize").toString())) {
            size = Integer.parseInt(resultMap.get("pageSize").toString());//每页条数
            number = Integer.parseInt(resultMap.get("pageNumber").toString());//开始索引
            page = (number / size) + 1;//当前页码

            number = (number - 1) * size;

            resultMap.put("number", number);
            resultMap.put("size", size);
            resultMap.put("limit", 1);
        }

        List<Map<String, Object>> list = dicIndexItemMapper.selectSonTableList(resultMap);
        //加序号
        for (int i = 1; i <= list.size(); i++) {
            list.get(i - 1).put("RN", i + number);
        }

        int total = dicIndexItemMapper.selectSonTableListCount(resultMap);

        Map<String, Object> ret = new HashMap<>();

        ret.put("rows", list);
        ret.put("total", total);
        ret.put("page", page);

        return ret;
    }

    //保存子表信息
    @Override
    public boolean saveSonTable(Map<String, Object> resultMap) {

        boolean b = false;

        int t = dicIndexItemMapper.saveSonTable(resultMap);
        if (t > 0) {
            DicIndexItem dit = new DicIndexItem();
            getAllList(dit);
            b = true;
        } else {
            b = false;
        }

        return b;
    }

    //根据子表id查询该表字段信息
    @Override
    public Map<String, Object> getFiledList(Map<String, Object> resultMap) {

        //分页查询条件
        int number = 0;
        int size = 0;
        int page = 0;
        if (resultMap.get("pageSize") != null && !"".equals(resultMap.get("pageSize").toString())) {
            size = Integer.parseInt(resultMap.get("pageSize").toString());//每页条数
            number = Integer.parseInt(resultMap.get("pageNumber").toString());//开始索引
            page = (number / size) + 1;//当前页码

            number = (number - 1) * size;

            resultMap.put("number", number);
            resultMap.put("size", size);
            resultMap.put("limit", 1);
        }

        List<Map<String, Object>> list = dicIndexItemMapper.selectFiledList(resultMap);
        //加序号
        for (int i = 1; i <= list.size(); i++) {
            list.get(i - 1).put("RN", i + number);
        }

        int total = dicIndexItemMapper.selectFiledCount(resultMap);

        Map<String, Object> ret = new HashMap<>();

        ret.put("rows", list);
        ret.put("total", total);
        ret.put("page", page);

        return ret;
    }

    //保存字段信息
    @Override
    public Map<String, Object> saveFiled(Map<String, Object> resultMap) {

        int state = 0;
        String msg = "";
        Map<String, Object> map = new HashMap<>();


        int t = dicIndexItemMapper.saveFiled(resultMap);
        if (t > 0) {

            String pkid = dicIndexItemMapper.getPkId(resultMap);
            resultMap.put("pkid", pkid);

            if ("4".equals(resultMap.get("filedType")) || "5".equals(resultMap.get("filedType")) || "6".equals(resultMap.get("filedType"))) {
                List<DicFieldType> list = new ArrayList<DicFieldType>();
                String singleSelect = resultMap.get("singleSelect").toString().isEmpty() ? null : resultMap.get("singleSelect").toString();
                String multipleSelect = resultMap.get("multipleSelect").toString().isEmpty() ? null : resultMap.get("multipleSelect").toString();
                String rediaoType = resultMap.get("rediaoType").toString().isEmpty() ? null : resultMap.get("rediaoType").toString();

                if (singleSelect != null) {
                    String[] single = singleSelect.split(",");
                    for (int i = 0; i < single.length; i++) {

                        DicFieldType dit = new DicFieldType();
                        dit.setFkIndexItemId(Integer.parseInt(pkid));
                        dit.setSingleSelect(single[i]);

                        list.add(dit);
                    }
                } else if (multipleSelect != null) {
                    String[] multiple = multipleSelect.split(",");
                    for (int i = 0; i < multiple.length; i++) {

                        DicFieldType dit = new DicFieldType();
                        dit.setFkIndexItemId(Integer.parseInt(pkid));
                        dit.setMultipleSelect(multiple[i]);

                        list.add(dit);
                    }
                } else if (rediaoType != null) {
                    String[] rediao = rediaoType.split(",");
                    for (int i = 0; i < rediao.length; i++) {

                        DicFieldType dit = new DicFieldType();
                        dit.setFkIndexItemId(Integer.parseInt(pkid));
                        dit.setRediaoType(rediao[i]);

                        list.add(dit);
                    }
                }

                int i = dicFieldTypeMapper.saveFiledValueBatch(list);
                if (i > 0) {
                    DicIndexItem dit = new DicIndexItem();
                    getAllList(dit);

                    int id = dicIndexItemMapper.selectId();
                    state = 0000;
                    msg = String.valueOf(id);
                } else {
                    state = 9999;
                    msg = "保存失败！！";
                }
            } else {
                //字段值类型判断
                DicFieldType dit = new DicFieldType();
                dit.setFkIndexItemId(Integer.parseInt(pkid));
                dit.setMax(resultMap.get("max").toString().isEmpty() ? null : resultMap.get("max").toString());
                dit.setMin(resultMap.get("min").toString().isEmpty() ? null : resultMap.get("min").toString());
                dit.setDecimalPoint(resultMap.get("decimalPoint").toString().isEmpty() ? null : resultMap.get("decimalPoint").toString());
                dit.setStringLength(resultMap.get("stringLength").toString().isEmpty() ? null : resultMap.get("stringLength").toString());
                dit.setSingleSelect(resultMap.get("singleSelect").toString().isEmpty() ? null : resultMap.get("singleSelect").toString());
                dit.setMultipleSelect(resultMap.get("multipleSelect").toString().isEmpty() ? null : resultMap.get("multipleSelect").toString());
                dit.setRediaoType(resultMap.get("rediaoType").toString().isEmpty() ? null : resultMap.get("rediaoType").toString());
                dit.setTimeLimit(resultMap.get("timeLimit").toString().isEmpty() ? null : resultMap.get("timeLimit").toString());
                dit.setDataStatus(0);

                int i = dicFieldTypeMapper.insert(dit);
                if (i > 0) {
                    DicIndexItem di = new DicIndexItem();
                    getAllList(di);

                    int id = dicIndexItemMapper.selectId();
                    state = 0000;
                    msg = String.valueOf(id);
                } else {
                    state = 9999;
                    msg = "保存失败！！";
                }

            }
        } else {
            state = 9999;
            msg = "保存失败！！";
        }

        map.put("state", state);
        map.put("msg", msg);

        return map;
    }

    //删除字段信息
    @Override
    public boolean deleteFiled(String pkId) {

        boolean b = false;

        int t = dicIndexItemMapper.deleteFiled(pkId);
        if (t > 0) {
            DicIndexItem dit = new DicIndexItem();
            getAllList(dit);
            b = true;
        } else {
            b = false;
        }
        return b;
    }

    //删除子表
    @Override
    public Map<String, Object> deleteSonTable(String pkId) {

        Map<String, Object> map = new HashMap<String, Object>();
        String msg = null;
        String code = null;

        List<Map<String, Object>> list = dicIndexItemMapper.selectSonTableInfo(pkId);
        if (list != null && list.size() > 0) {
            code = "9999";
            msg = "该表已被用，不能删除！！";
        } else {

            int t = dicIndexItemMapper.deleteSonTable(pkId);
            if (t > 0) {

                int i = dicIndexItemMapper.deleteFiledType(pkId);
                if (i > 0) {
                    DicIndexItem dit = new DicIndexItem();
                    getAllList(dit);
                    code = "0000";
                    msg = "删除成功！！";
                } else {
                    code = "9999";
                    msg = "删除失败！！";
                }
            } else {
                code = "9999";
                msg = "删除失败！！";
            }
        }

        map.put("msg", msg);
        map.put("code", code);

        return map;
    }

    //修改子表信息
    @Override
    public boolean updateSonTable(Map<String, Object> resultMap) {

        boolean b = false;
        //先修改原数据状态
        int i = dicIndexItemMapper.deleteSonTable(resultMap.get("pkId").toString());
        if (i > 0) {
            //在新增一条数据
            int t = dicIndexItemMapper.saveSonTable(resultMap);
            if (t > 0) {
                String pkid = dicIndexItemMapper.getTablePkId(resultMap);//得到新增子表主键
                resultMap.put("indexItemId", pkid);
                //修改字段表的关联id
                int n = dicIndexItemMapper.updateFiledById(resultMap);
                if (n > 0) {
                    DicIndexItem dit = new DicIndexItem();
                    getAllList(dit);
                    b = true;
                } else {
                    b = false;
                }
            } else {
                b = false;
            }
        } else {
            b = false;
        }

        return b;
    }

    //修改字段信息
    @Override
    public boolean updateFiled(Map<String, Object> resultMap) {

        boolean b = false;
        //先修改原数据状态
        int m = dicIndexItemMapper.deleteFiledTypeById(resultMap.get("pkId").toString());
        if (m > 0) {
            //在新增一条数据
            int t = dicIndexItemMapper.saveFiled(resultMap);
            if (t > 0) {
                //修改原值字段值的状态
                int n = dicFieldTypeMapper.updateFiledTypeById(resultMap.get("pkId").toString());
                if (n > 0) {
                    //得到新增字段主键
                    String pkid = dicIndexItemMapper.getPkId(resultMap);
                    resultMap.put("pkid", pkid);
                    if ("4".equals(resultMap.get("filedType")) || "5".equals(resultMap.get("filedType")) || "6".equals(resultMap.get("filedType"))) {
                        List<DicFieldType> list = new ArrayList<DicFieldType>();
                        String singleSelect = resultMap.get("singleSelect").toString().isEmpty() ? null : resultMap.get("singleSelect").toString();
                        String multipleSelect = resultMap.get("multipleSelect").toString().isEmpty() ? null : resultMap.get("multipleSelect").toString();
                        String rediaoType = resultMap.get("rediaoType").toString().isEmpty() ? null : resultMap.get("rediaoType").toString();

                        if (singleSelect != null) {
                            String[] single = singleSelect.split(",");
                            for (int i = 0; i < single.length; i++) {

                                DicFieldType dit = new DicFieldType();
                                dit.setFkIndexItemId(Integer.parseInt(pkid));
                                dit.setSingleSelect(single[i]);

                                list.add(dit);
                            }
                        } else if (multipleSelect != null) {
                            String[] multiple = multipleSelect.split(",");
                            for (int i = 0; i < multiple.length; i++) {

                                DicFieldType dit = new DicFieldType();
                                dit.setFkIndexItemId(Integer.parseInt(pkid));
                                dit.setMultipleSelect(multiple[i]);

                                list.add(dit);
                            }
                        } else if (rediaoType != null) {
                            String[] rediao = rediaoType.split(",");
                            for (int i = 0; i < rediao.length; i++) {

                                DicFieldType dit = new DicFieldType();
                                dit.setFkIndexItemId(Integer.parseInt(pkid));
                                dit.setRediaoType(rediao[i]);

                                list.add(dit);
                            }
                        }

                        int i = dicFieldTypeMapper.saveFiledValueBatch(list);
                        if (i > 0) {
                            DicIndexItem dit = new DicIndexItem();
                            getAllList(dit);
                            b = true;
                        } else {
                            b = false;
                        }
                    } else {
                        //字段值类型判断
                        DicFieldType dit = new DicFieldType();
                        dit.setFkIndexItemId(Integer.parseInt(pkid));
                        dit.setMax(resultMap.get("max").toString().isEmpty() ? null : resultMap.get("max").toString());
                        dit.setMin(resultMap.get("min").toString().isEmpty() ? null : resultMap.get("min").toString());
                        dit.setDecimalPoint(resultMap.get("decimalPoint").toString().isEmpty() ? null : resultMap.get("decimalPoint").toString());
                        dit.setStringLength(resultMap.get("stringLength").toString().isEmpty() ? null : resultMap.get("stringLength").toString());
                        dit.setSingleSelect(resultMap.get("singleSelect").toString().isEmpty() ? null : resultMap.get("singleSelect").toString());
                        dit.setMultipleSelect(resultMap.get("multipleSelect").toString().isEmpty() ? null : resultMap.get("multipleSelect").toString());
                        dit.setRediaoType(resultMap.get("rediaoType").toString().isEmpty() ? null : resultMap.get("rediaoType").toString());
                        dit.setTimeLimit(resultMap.get("timeLimit").toString().isEmpty() ? null : resultMap.get("timeLimit").toString());
                        dit.setDataStatus(0);

                        int i = dicFieldTypeMapper.insert(dit);
                        if (i > 0) {
                            DicIndexItem di = new DicIndexItem();
                            getAllList(di);
                            b = true;
                        } else {
                            b = false;
                        }

                    }
                } else {
                    b = false;
                }
            } else {
                b = false;
            }
        } else {
            b = false;
        }

        return b;
    }

    @Override
    public List<Map<String, Object>> getSurveyTableList(String redlineName, String tableName, Integer pageNum, Integer pageSize) {
        List<Map<String, Object>> list = dicIndexItemMapper.selectSurveyTableList(redlineName, tableName, (pageNum - 1) * pageSize, pageSize);
        return list;
    }

    @Override
    public int getTotal(String redlineName, String tableName) {
        int total = dicIndexItemMapper.selectTotal(redlineName, tableName);
        return total;
    }

    @Override
    public Map<String, Object> getTableInfo(Integer id) {
        Map<String, Object> mapInfo = dicIndexItemMapper.selectTableInfo(id);
        if (mapInfo != null && mapInfo.size() > 0) {

            String s = (String) mapInfo.get("survey_jsonData");
            //json转为数组
            JSONArray myJsonArray = JSONArray.fromObject(s);
            List<Map<String, Object>> mapListJson = (List) myJsonArray;

            List<Map<String, Object>> list = new ArrayList<>();
            //获取表拥有的所有字段
            List<Map<String, Object>> filedMapList = dicFieldTypeMapper.selectFiledMap(id);

            if (mapListJson != null && mapListJson.size() > 0) {

                for (Map<String, Object> map : mapListJson) {
                    Map<String, Object> filedMap = new HashMap<>();
                    Integer pkId = Integer.parseInt(map.get("pkId").toString());
                    String name = dicIndexItemMapper.selectName(pkId);
                    String value = (String) map.get("pkValue");
                    //根据id获取字段字段的类型
                    Integer filedType = dicFieldTypeMapper.selectFiledTypeById(pkId);
                    //构造单选下拉框
                    if (filedType == 4) {
                        filedMap.put("key", name);
                        //value 为字段的具体值 ，一个接口
                        if (StringUtils.isNotBlank(value)) {
                            Integer filedId = Integer.parseInt(value);
                            value = dicFieldTypeMapper.selectSingleValue(filedId);
                        }

                        //获取id遍历查询
                        filedMap.put("value", value);
                    } else if (filedType == 6) {
                        if (StringUtils.isNotBlank(value)) {
                            Integer filedId = Integer.parseInt(value);
                            value = dicFieldTypeMapper.selectRadioValue(filedId);
                        }
                        filedMap.put("key", name);
                        filedMap.put("value", value);

                    } else if (filedType == 5) {
                        filedMap.put("key", name);
                        List<String> filedList = null;
                        if (StringUtils.isNotBlank(value)) {
                            List<Integer> collect = null;

                            if (value.contains(",")) {
                                String[] ids = value.split(",");
                                List<String> strings = Arrays.asList(ids);

                                collect = strings.stream().map(e -> Integer.parseInt(e)).collect(Collectors.toList());
                            } else {
                                collect.add(Integer.parseInt(value));
                            }
                            //遍历集合去查询 查询到的值以分号分隔成一个字符串
                            filedList = dicFieldTypeMapper.getMutilValue(collect);
                        }

                        StringBuilder values = new StringBuilder();
                        if (filedList != null && filedList.size() > 0) {
                            for (String s1 : filedList) {
                                values.append(s1 + ";");
                            }
                            filedMap.put("value", values);
                        } else {
                            filedMap.put("value", value);
                        }



                        /* List<String>*/
                    } else {
                        filedMap.put("key", name);
                        filedMap.put("value", value);
                    }

                    list.add(filedMap);
                    //过滤集合中没有的字段
                    if (filedMapList != null && filedMapList.size() > 0) {
                        Iterator<Map<String, Object>> mapIterator = filedMapList.iterator();

                        while (mapIterator.hasNext()) {
                            Map<String, Object> stringObjectMap = mapIterator.next();

                            Integer filedId = (Integer) stringObjectMap.get("pk_id");
                            if (filedId == pkId) {
                                mapIterator.remove();
                            }
                        }
                    }
                }
            }

            //构造数据，没有的字段设置为空
            /*if (filedMapList != null && filedMapList.size() > 0) {
                for (Map<String, Object> map : filedMapList) {
                    Map<String, Object> filedMap2 = new HashMap<>();
                    Integer filedId = (Integer) map.get("pk_id");
                    String name = dicIndexItemMapper.selectName(filedId);
                    filedMap2.put("key", name);
                    filedMap2.put("value", "");
                    list.add(filedMap2);
                }
            }*/
            mapInfo.put("filed_name", list);
            mapInfo.remove("survey_jsonData");
        }
        return mapInfo;
    }

    @Override
    public List<SurveyTableInfoVO> getFileds(Integer id) {
        List<SurveyTableInfoVO> newList = null;
        try {
            List<SurveyTableInfoVO> list = dicIndexItemMapper.selectFileds(id);
            //单选框
            List<SurveyTableInfoVO> singleFilterList = new ArrayList<>();
            //多选框
            List<SurveyTableInfoVO> mutilFilterList = new ArrayList<>();
            //单选按钮
            List<SurveyTableInfoVO> redioFilterList = new ArrayList<>();
            //从集合中过滤出单选框和多选框的数据
            if (list != null && list.size() > 0) {
                for (SurveyTableInfoVO surveyTableInfoVO : list) {
                    if (surveyTableInfoVO.getFiledType() == 4) {
                        singleFilterList.add(surveyTableInfoVO);
                    }
                    if (surveyTableInfoVO.getFiledType() == 5) {
                        mutilFilterList.add(surveyTableInfoVO);
                    }
                    if (surveyTableInfoVO.getFiledType() == 6) {
                        redioFilterList.add(surveyTableInfoVO);
                    }
                }
            }
           //集合中相同元素去重
            List<SurveyTableInfoVO> distinctList = list.stream().collect(//list是需要去重的list，返回值是去重后的list
                    Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getId()))),//o代表object对象，o.list对象的属性值，根据此属性值去重
                            ArrayList::new));


            //过滤出单选框中id相同的数据
            Map<Integer, List<SurveyTableInfoVO>> sigleGroupBy = singleFilterList.stream().collect(Collectors.groupingBy(SurveyTableInfoVO::getId));
            //过滤出多选框中id相同的数据
            Map<Integer, List<SurveyTableInfoVO>> multiGroupBy = mutilFilterList.stream().collect(Collectors.groupingBy(SurveyTableInfoVO::getId));
            //过滤出单选按钮中id相同的数据
            Map<Integer, List<SurveyTableInfoVO>> redioGroupBy = redioFilterList.stream().collect(Collectors.groupingBy(SurveyTableInfoVO::getId));


            List<SurveyTableInfoVO> finalList = new ArrayList<>();
            //单选按钮
            if (redioGroupBy != null && redioGroupBy.size() > 0) {
                Set<Integer> key = redioGroupBy.keySet();
                for (Integer keys : key) {
                    List<String> list1 = new ArrayList<>();
                    for (SurveyTableInfoVO surveyTableInfoVO : redioGroupBy.get(keys)) {
                        list1.add(surveyTableInfoVO.getRediaoType());
                    }
                    //删除返回集合中相同id的所有数据
                    list.removeIf((SurveyTableInfoVO s) -> s.getId() == keys);
                    //构造数据
                    if(distinctList!=null&&distinctList.size()>0){
                        for (SurveyTableInfoVO surveyTableInfoVO : distinctList) {
                            if(surveyTableInfoVO.getId()==keys){
                                surveyTableInfoVO.setSelects(list1);
                            }
                        }
                    }
                   /* redioGroupBy.get(keys).get(0).setSelects(list1);
                    //添加到返回的集合中
                    finalList.add(redioGroupBy.get(keys).get(0));*/
                }
            }

            //单选框
            if (sigleGroupBy != null && sigleGroupBy.size() > 0) {
                Set<Integer> key = sigleGroupBy.keySet();
                for (Integer keys : key) {
                    List<String> list1 = new ArrayList<>();
                    for (SurveyTableInfoVO surveyTableInfoVO : sigleGroupBy.get(keys)) {
                        list1.add(surveyTableInfoVO.getSingleSelect());
                    }
                    //删除返回集合中相同id的所有数据
                    list.removeIf((SurveyTableInfoVO s) -> s.getId() == keys);
                    //构造数据
                    //构造数据
                    if(distinctList!=null&&distinctList.size()>0){
                        for (SurveyTableInfoVO surveyTableInfoVO : distinctList) {
                            if(surveyTableInfoVO.getId()==keys){
                                surveyTableInfoVO.setSelects(list1);
                            }
                        }
                    }
                  /*  sigleGroupBy.get(keys).get(0).setSelects(list1);
                    //添加到返回的集合中
                    finalList.add(sigleGroupBy.get(keys).get(0));*/
                }
            }
            //多选框
            if (multiGroupBy != null && multiGroupBy.size() > 0) {
                Set<Integer> key = multiGroupBy.keySet();
                for (Integer keys : key) {
                    List<String> list1 = new ArrayList<>();
                    for (SurveyTableInfoVO surveyTableInfoVO : multiGroupBy.get(keys)) {
                        list1.add(surveyTableInfoVO.getMultipleSelect());
                    }
                    //构造数据
                    if(distinctList!=null&&distinctList.size()>0){
                        for (SurveyTableInfoVO surveyTableInfoVO : distinctList) {
                            if(surveyTableInfoVO.getId()==keys){
                                surveyTableInfoVO.setSelects(list1);
                            }
                        }
                    }
                }
            }
            newList = distinctList.stream().sorted(Comparator.comparing(SurveyTableInfoVO::getSort)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询字段列表失败，异常信息:{}", e.getMessage());
            return null;
        }
        return newList;

    }

    @Override
    @Transactional
    public boolean deleteFileds(Integer id) {
        //根据字段id删除表字段
        int result = dicIndexItemMapper.deleteFileds(id);
        //删除关联字段值的所有数据
        int result1 = dicIndexItemMapper.deleteFiledValues(id);
        if (result > 0 && result1 > 0) {
            getAllList(new DicIndexItem());
            return true;
        }
        return false;
    }

    @Override
    public boolean editSort(List<Map<String, Object>> list) {
        try {
            for (Map<String, Object> map : list) {
                int result = dicIndexItemMapper.updateSort(map);
            }
        } catch (Exception e) {
            log.error("更新表字段值失败，异常信息为，{}", e.getMessage());
            return false;
        }

        return true;
    }

}
