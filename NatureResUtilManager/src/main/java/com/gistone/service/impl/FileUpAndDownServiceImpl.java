package com.gistone.service.impl;

import com.gistone.entity.MessageProperties;
import com.gistone.service.FileUpAndDownService;
import com.gistone.util.ResultMsg;
import com.gistone.util.UnRARUtil;
import com.gistone.util.ZipUtil;
import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class FileUpAndDownServiceImpl implements FileUpAndDownService {

    @Autowired
    private  MessageProperties config;
    //这里是供给所有的上传接口调用
    private static final Map<String,String> map = new HashMap<>();

    private  Map<String,String> doMap(Map<String,String> map){

        map.put("nr_system",config.getNr_system());
        map.put("nr_ledger",config.getNr_ledger());
        map.put("nr_object",config.getNr_object());
        map.put("nr_reserve",config.getNr_reserve());
        map.put("nr_point",config.getNr_point());
        map.put("nr_other",config.getNr_other());
        map.put("nr_decode",config.getNr_decode());


        return map;

    }


    @Override
    public void deletePicture(String path) {
        File file = new File(path);
        if(file.exists()){
            file.delete();
        }
    }
    @Override
    public Map<String, Object> uploadPicture(MultipartFile[] files,String dirId) {
        doMap(map);
        Map<String, Object> resMap = new HashMap<>();

        JSONObject json = new JSONObject();
        JSONArray jarr =new JSONArray();
        // 重新生成
        String newUUID = UUID.randomUUID().toString().replaceAll("-", "");
        try {
            Integer sucNum=0;
            if(files.length>0){
                for (MultipartFile file: files) {

                    String[] image_type_arr = config.getImageType().split(",");
                    String path = null;
                    boolean flag = true;
                    if (flag) {
                        resMap.put("result", 1000);
                        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                        // 获得文件类型
                        String fileType =file.getOriginalFilename();
                                //file.getContentType();
                        // 获得文件后缀名称
                        //开始处理

                        //String sufName = fileType.substring(fileType.indexOf("/") + 1);
                        String sufName = fileType.substring(fileType.lastIndexOf(".") );
                        if(map.get(dirId)==null||!map.get(dirId).toString().contains(sufName.toLowerCase())){
                            resMap.put("result", "文件格式不正确,上传失败");
                            return resMap;
                        }
                        //if()
                        // 原名称
                        String oldFileName = file.getOriginalFilename();
                        // 新名称
                        String newFileName = uuid + "." + sufName;
                        // 年月日文件夹
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                        String baseDateDir = sdf.format(new Date());


                        // 进行压缩(大于20M)
                        if (file.getSize() >config.getFileSize()) {

                            newFileName = newUUID + "." + sufName;
                            path = config.getUpPath() + "/" + dirId + "/" + baseDateDir +"/" + newUUID + sufName;
                            // 如果目录不存在则创建目录
                            File oldFile = new File(path);
                            File oldFilePar = oldFile.getParentFile();
                            if (!oldFilePar.exists()) {
                                oldFilePar.mkdirs();
                            }
                            oldFile.createNewFile();
                            file.transferTo(oldFile);
                            // 压缩文件
                            Thumbnails.of(oldFile).scale(config.getScaleRatio()).toFile(path);
                            // 显示路径
                            json.put("path", "/ld/" +  dirId + "/" + baseDateDir +"/" + newUUID  + sufName);
                        } else {
                            path = config.getUpPath() + "/" +  dirId + "/" + baseDateDir + "/" + uuid   + sufName;
                            // 如果目录不存在则创建目录
                            File uploadFile = new File(path);
                            File uploadFilePar = uploadFile.getParentFile();
                            if (!uploadFilePar.exists()) {
                                uploadFilePar.mkdirs();
                            }
                            uploadFile.createNewFile();
                            file.transferTo(uploadFile);
                            // 显示路径
                            json.put("path", "/ld/" +  dirId + "/" + baseDateDir + "/" + uuid  + sufName);
                        }
                        //如果上传的文件是解译数据，则解压到动态工作空间
                        String shpName = null;
                        if("nr_decode".equals(dirId)){
                            if(".rar".equals(sufName.toLowerCase())){
                                UnRARUtil.unRarByCmd(config.getUpPath() +"/" +  dirId + "/" + baseDateDir + "/" + uuid  + sufName,config.getUpPath() + "/dynamicLayerSpace");
                                //shpName = UnRARUtil.unRarArcGISFiles(config.getUpPath() +"/" +  dirId + "/" + baseDateDir + "/" + uuid  + sufName ,config.getUpPath() + "/dynamicLayerSpace",newUUID);
                            }else{
                                shpName = ZipUtil.unZipArcGISFiles(config.getUpPath() +"/" +  dirId + "/" + baseDateDir + "/" + uuid  + sufName,config.getUpPath() + "/dynamicLayerSpace",newUUID);
                            }
                            json.put("path", shpName);
                            //解译结果，报告预览地址
                            json.put("cv010", "");
                        }
                        System.out.println(shpName);
                        json.put("oldFileName", oldFileName);
                        jarr.add(json);
                        resMap.put("data",jarr);
                       // resMap.put("fileSize", file.getSize());
                        sucNum++;
                    } else {
                        resMap.put("result", "文件格式不正确,上传失败");
                    }

                }
                if(sucNum==files.length){
                    resMap.put("result",ResultMsg.MSG_1000);
                }else {
                    resMap.put("result",ResultMsg.MSG_1006);
                }

            }else{
                //返回空，安卓要求
                json.put("path","");
                json.put("oldFileName","");
                jarr.add(json);
                resMap.put("result",ResultMsg.MSG_1000);
                resMap.put("data",jarr);
            }

            return resMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resMap;


    }


}
