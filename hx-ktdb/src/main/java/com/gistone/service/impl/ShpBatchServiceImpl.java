package com.gistone.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.DataRedlineRegister;
import com.gistone.entity.LmBoard;
import com.gistone.entity.LmMarkerMobile;
import com.gistone.entity.ShpBatch;
import com.gistone.exception.ImportException;
import com.gistone.mapper.DataRedlineRegisterMapper;
import com.gistone.mapper.LmBoardMapper;
import com.gistone.mapper.LmMarkerMobileMapper;
import com.gistone.mapper.ShpBatchMapper;
import com.gistone.service.ShpBatchService;
import com.gistone.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

import static com.gistone.util.HttpRequestUtil.httpRequest;

/**
 * <p>
 * 预设数据文件批次表 服务实现类
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-10-29
 */
@Service
@Transactional
@Slf4j
public class ShpBatchServiceImpl extends ServiceImpl<ShpBatchMapper, ShpBatch> implements ShpBatchService {

    @Autowired
    private ShpBatchMapper mapper;

    @Autowired
    private DataRedlineRegisterMapper dataRedlineRegisterMapper;

    @Autowired
    private LmMarkerMobileMapper lmMarkerMobileMapper;

    @Autowired
    private LmBoardMapper lmBoardMapper;

    @Override
    public Map<String, Object> list(Integer pageNum, Integer pageSize, String userName) {

        QueryWrapper<ShpBatch> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userName)) {
            //wrapper.likeRight("SA008",userName);
        }
        // wrapper.eq("SA007",1);
        //wrapper.orderByDesc("SA003");
        IPage<ShpBatch> iPage = mapper.selectPage(new Page<>(pageNum, pageSize), wrapper);


        Map<String, Object> result = new HashMap<>();
        result.put("rows", iPage.getRecords());
        result.put("total", iPage.getTotal());

        return result;
    }

    @Override
    public void delete(List<Integer> ids) {
        //具体逻辑

    }

    @Override
    public void insert(ShpBatch entity) {
        //具体逻辑

    }


    @Override
    public void edit(ShpBatch entity) {
        //具体逻辑
    }

    @Override
    public void importPreRedlineDate(String url, String remark) {
        //获取服务数据
        try {
            String s = httpRequest(url, "GET", null);

            JSONObject parse = JSON.parseObject(s);
            JSONArray jsonArray = (JSONArray) parse.get("features");
            //将数据写入shp文件
            String fileUrl = PathUtile.getRandomPath("D:/epr/shp/", "x.shp");
            System.out.println(fileUrl);
            ShpUtil.importPreRedlinedata(jsonArray, fileUrl);
            //批次表中录入数据
            ShpBatch shpBatch = new ShpBatch().setShpUrl(fileUrl.substring(2)).setCreateDate(LocalDateTime.now()).setServiceUrl(url).setCreateBy(1).setType(1);
            if (StringUtils.isNotBlank(remark)) {
                shpBatch.setRemark(remark);
            }
            mapper.insert(shpBatch);
            //读取shp文件的数据
            ImportRedlineData importRedlineData = new ImportRedlineData();
            ArrayList<DataRedlineRegister> lmMarkerMobiles = importRedlineData.readShapeFile(fileUrl);

            //清空原数据
            dataRedlineRegisterMapper.delete(null);
            //再批量插入
            if (lmMarkerMobiles != null && lmMarkerMobiles.size() > 0) {
                for (DataRedlineRegister lmMarkerMobile : lmMarkerMobiles) {
                    dataRedlineRegisterMapper.insert(lmMarkerMobile);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("导入预置红线数据失败，异常信息为:", e.getMessage());
            throw new ImportException(ResultEnum.ERROR.getCode(), "导入预置红线数据失败");
        }

        System.out.println("======导入红线数据完成=======");
    }

    @Override
    public void importPreMarkerData(String url, String remark) {
        //从服务获取数据
        try {
            String s = httpRequest(url, "GET", null);

            JSONObject parse = JSON.parseObject(s);
            JSONArray jsonArray = (JSONArray) parse.get("features");
            //将数据写入shp文件
            String fileUrl = PathUtile.getRandomPath("D:/epr/shp/", "x.shp");
            System.out.println(fileUrl);

            ShpUtil.importPreMarkerdata(jsonArray, fileUrl);
            //批次表中录数据
            ShpBatch shpBatch = new ShpBatch().setShpUrl(fileUrl.substring(2)).setCreateDate(LocalDateTime.now()).setServiceUrl(url).setCreateBy(1).setType(2);
            if (StringUtils.isNotBlank(remark)) {
                shpBatch.setRemark(remark);
            }
            mapper.insert(shpBatch);
            //读shp录数据
            ReadShapeFile readShapeFile = new ReadShapeFile();
            ArrayList<LmMarkerMobile> lmMarkerMobiles = readShapeFile.readShapeFile(fileUrl);

            if (lmMarkerMobiles != null && lmMarkerMobiles.size() > 0) {

                for (int i = 0; i < lmMarkerMobiles.size(); i++) {
                    String ss = "";
                    if ((i + "").length() == 1) {
                        ss = "000" + i;
                    } else if ((i + "").length() == 2) {
                        ss = "00" + i;
                    } else if ((i + "").length() == 3) {
                        ss = "0" + i;
                    }
                    lmMarkerMobiles.get(i).setType(0);
                    //                lmMarkerMobiles.get(i).setJzNumber(lmMarkerMobiles.get(i).getJzNumber() + "-" + ss);
                    lmMarkerMobiles.get(i).setSaveTime(new Date());
                }
            }

            System.out.println(lmMarkerMobiles);

            //先将之前的数据删除
            lmMarkerMobileMapper.delete(null);
            //再批量插入
            if (lmMarkerMobiles != null && lmMarkerMobiles.size() > 0) {
                for (LmMarkerMobile lmMarkerMobile : lmMarkerMobiles) {
                    lmMarkerMobileMapper.insert(lmMarkerMobile);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("导入预置界桩数据失败，异常信息为:", e.getMessage());
            throw new ImportException(ResultEnum.ERROR.getCode(), "导入预置界桩数据失败");
        }
    }

    @Override
    public void importPreBoardData(String url, String remark) {
        try {
            String s = httpRequest(url, "GET", null);

            JSONObject parse = JSON.parseObject(s);
            JSONArray jsonArray = (JSONArray) parse.get("features");
            //将数据写入shp文件
            String fileUrl = PathUtile.getRandomPath("D:/epr/shp/", "x.shp");
            System.out.println(fileUrl);

            ShpUtil.importPreBoarddata(jsonArray, fileUrl);
            //批次表中录数据
            ShpBatch shpBatch = new ShpBatch().setShpUrl(fileUrl.substring(2)).setCreateDate(LocalDateTime.now()).setServiceUrl(url).setCreateBy(1).setType(2);
            if (StringUtils.isNotBlank(remark)) {
                shpBatch.setRemark(remark);
            }
            mapper.insert(shpBatch);

            //读shp并且导入数据

            String path = "D:/Work/gistone/static/shapefile/redline_p_bsp.shp";
            ImportBoardData importRedlineData = new ImportBoardData();
            ArrayList<LmBoard> lmMarkerMobiles = importRedlineData.readShapeFile(path);
            int num = 1;
            for (LmBoard lmMarkerMobile : lmMarkerMobiles) {
                lmMarkerMobile.setType(0);
                lmMarkerMobile.setSaveDate(new Date());
                lmMarkerMobile.setNumber(String.valueOf(num));
                num++;
            }
            lmBoardMapper.delete(null);
            if (lmMarkerMobiles != null && lmMarkerMobiles.size() > 0) {
                for (LmBoard lmMarkerMobile : lmMarkerMobiles) {
                    lmBoardMapper.insert(lmMarkerMobile);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("导入预置标识牌数据失败，异常信息为:", e.getMessage());
            throw new ImportException(ResultEnum.ERROR.getCode(), "导入预置标识牌数据失败");
        }
    }

}


