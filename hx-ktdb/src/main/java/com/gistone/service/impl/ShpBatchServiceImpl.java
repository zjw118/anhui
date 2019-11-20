package com.gistone.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.VO.ResultVO;
import com.gistone.entity.*;
import com.gistone.exception.ImportException;
import com.gistone.mapper.*;
import com.gistone.service.ShpBatchService;
import com.gistone.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
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
    private DataRedlineMapper dataRedlineMapper;

    @Autowired
    private LmMarkerMobileMapper lmMarkerMobileMapper;

    @Autowired
    private LmBoardMapper lmBoardMapper;

    private final static String URL = "/0/query?returnGeometry=true&f=json&where=0%3D0&outFields=*";
    @Value("${PATH}")
    private String PATH;

    @Value("${ftp_host}")
    private String ftpHost;
    @Value("${ftp_port}")
    private int ftpPort;
    @Value("${ftp_username}")
    private String ftpUserName;
    @Value("${ftp_password}")
    private String ftpPassword;
    @Value("${ftp.path}")
    private String ftpPath;
    @Override
    public ResultVO listShp (ShpBatch sb){
        List<ShpBatch> list = mapper.getBorderData(sb);
        return ResultVOUtil.success(list);
    }

    @Override
    public Map<String, Object> list(Integer pageNum, Integer pageSize, String userName) {

        QueryWrapper<ShpBatch> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userName)) {
            //wrapper.likeRight("SA008",userName);
        }
         wrapper.eq("type",1);
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
            //获取主键url
            String primaryKey = "/0/query?where=0%3D0&text=&objectIds=&time=&geometry=&geometryType=esriGeometryEnvelope&inSR=&spatialRel=esriSpatialRelIntersects&relationParam=&outFields=&returnGeometry=true&returnTrueCurves=false&maxAllowableOffset=&geometryPrecision=&outSR=&returnIdsOnly=true&returnCountOnly=false&orderByFields=&groupByFieldsForStatistics=&outStatistics=&returnZ=false&returnM=false&gdbVersion=&returnDistinctValues=false&resultOffset=&resultRecordCount=&f=json";

            String key = httpRequest(url + primaryKey, "GET", null);
            JSONObject jsonObject = JSON.parseObject(key);

            String pk = (String) jsonObject.get("objectIdFieldName");

            String queryParam = "/0/query?where=0%3D0&text=&objectIds=&time=&geometry=&geometryType=esriGeometryEnvelope&inSR=&spatialRel=esriSpatialRelIntersects&relationParam=&outFields=&returnGeometry=true&returnTrueCurves=false&maxAllowableOffset=&geometryPrecision=&outSR=&returnIdsOnly=false&returnCountOnly=true&orderByFields=&groupByFieldsForStatistics=&outStatistics=&returnZ=false&returnM=false&gdbVersion=&returnDistinctValues=false&resultOffset=&resultRecordCount=&f=json";
            String count = httpRequest(url + queryParam, "GET", null);
            JSONObject parse = JSON.parseObject(count);
            int counint = (int) parse.get("count");
            int num = counint / 1000;
            JSONArray jsonArray = new JSONArray();
            //将数据写入shp文件

            for (int i = 0; i < num + 1; i++) {
                String param = "/0/query?where=" + pk + "<%3D" + (1 + i) * 1000 + "+and+" + pk + ">" + i * 1000 + "&text=&objectIds=&time=&geometry=&geometryType=esriGeometryEnvelope&inSR=&spatialRel=esriSpatialRelIntersects&relationParam=&outFields=*&returnGeometry=true&returnTrueCurves=false&maxAllowableOffset=&geometryPrecision=&outSR=&returnIdsOnly=false&returnCountOnly=false&orderByFields=&groupByFieldsForStatistics=&outStatistics=&returnZ=false&returnM=false&gdbVersion=&returnDistinctValues=false&resultOffset=&resultRecordCount=&f=json";
                String sum = httpRequest(url + param, "GET", null);
                JSONObject parse1 = JSON.parseObject(sum);
                JSONArray jsonArray1 = (JSONArray) parse1.get("features");
                jsonArray.addAll(jsonArray1);
                System.out.println(i);
            }

            //将数据写入shp文件
            String fileUrl = PathUtile.getRandomPath(PATH + "/epr/shp/", "x.shp");
            System.out.println(fileUrl);

            ShpUtil.importPreRedlinedata(jsonArray, fileUrl);

            //读取shp文件的数据
            ImportRedlineData importRedlineData = new ImportRedlineData();
            ArrayList<DataRedlineRegister> lmMarkerMobiles = importRedlineData.readShapeFile(fileUrl);

            //清空原数据
            dataRedlineRegisterMapper.delete(null);
            dataRedlineMapper.delete(null);
            //再批量插入
            if (lmMarkerMobiles != null && lmMarkerMobiles.size() > 0) {
                for (DataRedlineRegister lmMarkerMobile : lmMarkerMobiles) {
                    dataRedlineRegisterMapper.insert(lmMarkerMobile);
                    DataRedline dataRedline = new DataRedline();
                    BeanUtils.copyProperties(lmMarkerMobile,dataRedline);
                    dataRedlineMapper.insert(dataRedline);
                }
            }

            //批次表中录入数据
            // 把生成的文件上传到ftp服务器的文件夹下面，并返回地址
            //1.获取文件夹下所有文件
            String path = fileUrl.substring(0, fileUrl.lastIndexOf("/")); // 路径
            File f = new File(path);
            if (!f.exists()) {
                System.out.println(path + " not exists");
                return;
            }
            File fa[] = f.listFiles();

            //加日期和UUId 创建文件夹

            String nowTime = DateUtils.format(new Date(), "yyyyMMdd");
            String token = UUID.randomUUID().toString().replaceAll("-", "");
            String  ftpPathUrl = "/redlineshp/" + nowTime + "-" + token + "-redline/";
            FTPUtil.createDri(ftpHost, ftpUserName, ftpPassword, ftpPort,ftpPathUrl);

            String shpName = "";
            for (int i = 0; i < fa.length; i++) {
                File fs = fa[i];
                if (fs.isDirectory()) {
                    System.out.println(fs.getName() + " [目录]");
                } else {
                    System.out.println(fs.getName());
                    FileInputStream input = new FileInputStream(new File(path + File.separatorChar + fs.getName()));
                    FTPUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPathUrl, fs.getName(), input);
                }
                if (fs.getName().contains("shp")) {
                    shpName = fs.getName();
                }
            }

            ShpBatch shpBatch = new ShpBatch().setShpUrl(fileUrl.substring(2)).setFtpShpUrl(ftpPath + ftpPathUrl + shpName).setCreateDate(LocalDateTime.now()).setServiceUrl(url).setCreateBy(1).setType(1);
            if (StringUtils.isNotBlank(remark)) {
                shpBatch.setRemark(remark);
            }
            mapper.insert(shpBatch);
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
//            String s = httpRequest(url+URL, "GET", null);
            //获取主键url
            String primaryKey = "/0/query?where=0%3D0&text=&objectIds=&time=&geometry=&geometryType=esriGeometryEnvelope&inSR=&spatialRel=esriSpatialRelIntersects&relationParam=&outFields=&returnGeometry=true&returnTrueCurves=false&maxAllowableOffset=&geometryPrecision=&outSR=&returnIdsOnly=true&returnCountOnly=false&orderByFields=&groupByFieldsForStatistics=&outStatistics=&returnZ=false&returnM=false&gdbVersion=&returnDistinctValues=false&resultOffset=&resultRecordCount=&f=json";

            String key = httpRequest(url + primaryKey, "GET", null);
            JSONObject jsonObject = JSON.parseObject(key);

            String pk = (String) jsonObject.get("objectIdFieldName");
            //获取总数url
            String queryParam = "/0/query?where=0%3D0&text=&objectIds=&time=&geometry=&geometryType=esriGeometryEnvelope&inSR=&spatialRel=esriSpatialRelIntersects&relationParam=&outFields=&returnGeometry=true&returnTrueCurves=false&maxAllowableOffset=&geometryPrecision=&outSR=&returnIdsOnly=false&returnCountOnly=true&orderByFields=&groupByFieldsForStatistics=&outStatistics=&returnZ=false&returnM=false&gdbVersion=&returnDistinctValues=false&resultOffset=&resultRecordCount=&f=json";
            String count = httpRequest(url + queryParam, "GET", null);
            JSONObject parse = JSON.parseObject(count);
            int counint = (int) parse.get("count");
            int num = counint / 1000;
            JSONArray jsonArray = new JSONArray();
            //将数据写入shp文件

            for (int i = 0; i < num + 1; i++) {
                String param = "/0/query?where=" + pk + "<%3D" + (1 + i) * 1000 + "+and+" + pk + ">" + i * 1000 + "&text=&objectIds=&time=&geometry=&geometryType=esriGeometryEnvelope&inSR=&spatialRel=esriSpatialRelIntersects&relationParam=&outFields=*&returnGeometry=true&returnTrueCurves=false&maxAllowableOffset=&geometryPrecision=&outSR=&returnIdsOnly=false&returnCountOnly=false&orderByFields=&groupByFieldsForStatistics=&outStatistics=&returnZ=false&returnM=false&gdbVersion=&returnDistinctValues=false&resultOffset=&resultRecordCount=&f=json";
                String sum = httpRequest(url + param, "GET", null);
                JSONObject parse1 = JSON.parseObject(sum);
                JSONArray jsonArray1 = (JSONArray) parse1.get("features");
                jsonArray.addAll(jsonArray1);
                System.out.println(i);
            }
//
            String fileUrl = PathUtile.getRandomPath(PATH + "/epr/shp/", "x.shp");
            System.out.println(fileUrl);
//            JSONArray objects = JSON.parseArray(sb.toString().substring(0,sb.toString().length()-1));
            ShpUtil.importPreMarkerdata(jsonArray, fileUrl);

            //读shp录数据
            ReadShapeFile readShapeFile = new ReadShapeFile();
            ArrayList<LmMarkerMobile> lmMarkerMobiles = readShapeFile.readShapeFile(fileUrl);
            System.out.println(lmMarkerMobiles.size());

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
            //创建文件夹并且长传生成的shp文件
            //把生成的文件上传到ftp服务器的文件夹下面，并返回地址
            //1.获取文件夹下所有文件
            String path = fileUrl.substring(0, fileUrl.lastIndexOf("/")); // 路径
            File f = new File(path);
            if (!f.exists()) {
                System.out.println(path + " not exists");
                return;
            }
            File fa[] = f.listFiles();

            //加日期和UUId 创建文件夹

            String nowTime = DateUtils.format(new Date(), "yyyyMMdd");
            String token = UUID.randomUUID().toString().replaceAll("-", "");
            String ftpPathUrl = "/redlineshp/" + nowTime + "-" + token + "-jz/";
            FTPUtil.createDri(ftpHost, ftpUserName, ftpPassword, ftpPort,ftpPathUrl );

            String shpName = "";
            for (int i = 0; i < fa.length; i++) {
                File fs = fa[i];
                if (fs.isDirectory()) {
                    System.out.println(fs.getName() + " [目录]");
                } else {
                    System.out.println(fs.getName());
                    FileInputStream input = new FileInputStream(new File(path + File.separatorChar + fs.getName()));
                    FTPUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPathUrl, fs.getName(), input);
                }
                if (fs.getName().contains("shp")) {
                    shpName = fs.getName();
                }
            }

            //批次表中录数据
            ShpBatch shpBatch = new ShpBatch().setShpUrl(fileUrl.substring(2)).setShpUrl(ftpPath + ftpPathUrl + shpName).setCreateDate(LocalDateTime.now()).setServiceUrl(url).setCreateBy(1).setType(2);
            if (StringUtils.isNotBlank(remark)) {
                shpBatch.setRemark(remark);
            }
            mapper.insert(shpBatch);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("导入预置界桩数据失败，异常信息为:", e.getMessage());
            throw new ImportException(ResultEnum.ERROR.getCode(), "导入预置界桩数据失败");
        }
    }

    @Override
    public void importPreBoardData(String url, String remark) {
        try {
            //获取主键url
            String primaryKey = "/0/query?where=0%3D0&text=&objectIds=&time=&geometry=&geometryType=esriGeometryEnvelope&inSR=&spatialRel=esriSpatialRelIntersects&relationParam=&outFields=&returnGeometry=true&returnTrueCurves=false&maxAllowableOffset=&geometryPrecision=&outSR=&returnIdsOnly=true&returnCountOnly=false&orderByFields=&groupByFieldsForStatistics=&outStatistics=&returnZ=false&returnM=false&gdbVersion=&returnDistinctValues=false&resultOffset=&resultRecordCount=&f=json";

            String key = httpRequest(url + primaryKey, "GET", null);
            JSONObject jsonObject = JSON.parseObject(key);

            String pk = (String) jsonObject.get("objectIdFieldName");

            String queryParam = "/0/query?where=0%3D0&text=&objectIds=&time=&geometry=&geometryType=esriGeometryEnvelope&inSR=&spatialRel=esriSpatialRelIntersects&relationParam=&outFields=&returnGeometry=true&returnTrueCurves=false&maxAllowableOffset=&geometryPrecision=&outSR=&returnIdsOnly=false&returnCountOnly=true&orderByFields=&groupByFieldsForStatistics=&outStatistics=&returnZ=false&returnM=false&gdbVersion=&returnDistinctValues=false&resultOffset=&resultRecordCount=&f=json";
            String count = httpRequest(url + queryParam, "GET", null);
            JSONObject parse = JSON.parseObject(count);
            int counint = (int) parse.get("count");
            int num1 = counint / 1000;
            JSONArray jsonArray = new JSONArray();
            //将数据写入shp文件

            for (int i = 0; i < num1 + 1; i++) {
                String param = "/0/query?where=" + pk + "<%3D" + (1 + i) * 1000 + "+and+" + pk + ">" + i * 1000 + "&text=&objectIds=&time=&geometry=&geometryType=esriGeometryEnvelope&inSR=&spatialRel=esriSpatialRelIntersects&relationParam=&outFields=*&returnGeometry=true&returnTrueCurves=false&maxAllowableOffset=&geometryPrecision=&outSR=&returnIdsOnly=false&returnCountOnly=false&orderByFields=&groupByFieldsForStatistics=&outStatistics=&returnZ=false&returnM=false&gdbVersion=&returnDistinctValues=false&resultOffset=&resultRecordCount=&f=json";
                String sum = httpRequest(url + param, "GET", null);
                JSONObject parse1 = JSON.parseObject(sum);
                JSONArray jsonArray1 = (JSONArray) parse1.get("features");
                jsonArray.addAll(jsonArray1);
                System.out.println(i);
            }
            //将数据写入shp文件
            String fileUrl = PathUtile.getRandomPath(PATH + "/epr/shp/", "x.shp");
            System.out.println(fileUrl);

            ShpUtil.importPreBoarddata(jsonArray, fileUrl);


            //读shp并且导入数据

            ImportBoardData importRedlineData = new ImportBoardData();
            ArrayList<LmBoard> lmMarkerMobiles = importRedlineData.readShapeFile(fileUrl);
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
            //创建文件夹并且长传生成的shp文件
            //把生成的文件上传到ftp服务器的文件夹下面，并返回地址
            //1.获取文件夹下所有文件
            String path = fileUrl.substring(0, fileUrl.lastIndexOf("/")); // 路径
            File f = new File(path);
            if (!f.exists()) {
                System.out.println(path + " not exists");
                return;
            }
            File fa[] = f.listFiles();

            //加日期和UUId 创建文件夹

            String nowTime = DateUtils.format(new Date(), "yyyyMMdd");
            String token = UUID.randomUUID().toString().replaceAll("-", "");
            String ftpPathUrl = "/redlineshp/" + nowTime + "-" + token + "-bsp/";
            FTPUtil.createDri(ftpHost, ftpUserName, ftpPassword, ftpPort,ftpPathUrl );

            String shpName = "";
            for (int i = 0; i < fa.length; i++) {
                File fs = fa[i];
                if (fs.isDirectory()) {
                    System.out.println(fs.getName() + " [目录]");
                } else {
                    System.out.println(fs.getName());
                    FileInputStream input = new FileInputStream(new File(path + File.separatorChar + fs.getName()));
                    FTPUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPathUrl, fs.getName(), input);
                }
                if (fs.getName().contains("shp")) {
                    shpName = fs.getName();
                }
            }


            //批次表中录数据
            ShpBatch shpBatch = new ShpBatch().setShpUrl(fileUrl.substring(2)).setFtpShpUrl(ftpPath + ftpPathUrl + shpName).setCreateDate(LocalDateTime.now()).setServiceUrl(url).setCreateBy(1).setType(3);
            if (StringUtils.isNotBlank(remark)) {
                shpBatch.setRemark(remark);
            }
            mapper.insert(shpBatch);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("导入预置标识牌数据失败，异常信息为:", e.getMessage());
            throw new ImportException(ResultEnum.ERROR.getCode(), "导入预置标识牌数据失败");
        }
    }

    @Override
    public List<ShpBatch> getNewList() {
        List<ShpBatch> shpBatches = mapper.getNewList();
        return shpBatches;
    }

    @Override
    public void importPreVector(String url, String remark) {
        ShpBatch shpBatch = new ShpBatch().setCreateDate(LocalDateTime.now()).setServiceUrl(url).setCreateBy(1).setType(5);
        if (StringUtils.isNotBlank(remark)) {
            shpBatch.setRemark(remark);
        }
        mapper.insert(shpBatch);

    }

    @Override
    public void importPreImage(String url, String remark) {
        ShpBatch shpBatch = new ShpBatch().setCreateDate(LocalDateTime.now()).setServiceUrl(url).setCreateBy(1).setType(6);
        if (StringUtils.isNotBlank(remark)) {
            shpBatch.setRemark(remark);
        }
        mapper.insert(shpBatch);
    }

    @Override
    public void importPreVectorMarker(String url, String remark) {
        ShpBatch shpBatch = new ShpBatch().setCreateDate(LocalDateTime.now()).setServiceUrl(url).setCreateBy(1).setType(7);
        if (StringUtils.isNotBlank(remark)) {
            shpBatch.setRemark(remark);
        }
        mapper.insert(shpBatch);
    }

    @Override
    public void importPreImageMarker(String url, String remark) {
        ShpBatch shpBatch = new ShpBatch().setCreateDate(LocalDateTime.now()).setServiceUrl(url).setCreateBy(1).setType(8);
        if (StringUtils.isNotBlank(remark)) {
            shpBatch.setRemark(remark);
        }
        mapper.insert(shpBatch);
    }

}


