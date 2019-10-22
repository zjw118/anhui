package com.gistone.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.St4ScsCp;
import com.gistone.mapper.St4ScsCpMapper;
import com.gistone.service.ISt4ScsCpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xxh
 * @since 2019-08-14
 */
@Service
public class St4ScsCpServiceImpl extends ServiceImpl<St4ScsCpMapper, St4ScsCp> implements ISt4ScsCpService {

    @Autowired
    private St4ScsCpMapper st4ScsCpMapper;

    @Override
    public List<Map<String, Object>> getSt4ScsCpInfo(String cp003, Integer cp006) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        QueryWrapper<St4ScsCp> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CP003", cp003);
        queryWrapper.eq("CP006", cp006);
        St4ScsCp st4ScsCp = st4ScsCpMapper.selectOne(queryWrapper);
        if (st4ScsCp != null) {
            if (st4ScsCp.getCp002() != null) {
                JSONObject jsonObject = JSONObject.parseObject(st4ScsCp.getCp002());
                JSONArray json = JSONArray.parseArray(jsonObject.get("data").toString());
                for (int i = 0; i < json.size(); i++) {
                    Map<String, Object> map = new HashMap<>();
                    JSONObject jsonObject1 = JSONObject.parseObject(json.get(i).toString());
                    map.put("id", jsonObject1.get("id").toString());
                    map.put("name", jsonObject1.get("name").toString());
                    map.put("isMust", jsonObject1.get("isMust").toString());
                    mapList.add(map);
                }
                return mapList;
            }
        }
        return null;
    }
}
