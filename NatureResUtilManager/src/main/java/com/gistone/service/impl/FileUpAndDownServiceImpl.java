package com.gistone.service.impl;

import com.gistone.entity.MessageProperties;
import com.gistone.service.FileUpAndDownService;
import com.gistone.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.tomcat.jni.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class FileUpAndDownServiceImpl implements FileUpAndDownService {

    @Autowired
    private  MessageProperties config;
    @Value("${PATH}")
    private String PATH;



    @Value("${ftp_host}")
    private String ftpHost;
    @Value("${ftp_port}")
    private Integer ftpPort;
    @Value("${ftp_username}")
    private String ftpUserName;
    @Value("${ftp_password}")
    private String ftpPassword;
    @Value("${ftp_pt}")
    private String ftpPt;
    @Value("${ftp_url}")
    private String ftpUrl;
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
        map.put("nr_temp",config.getNr_temp());


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
                            //Thumbnails.of(oldFile).scale(config.getScaleRatio()).toFile(path);
                            // 显示路径
                            json.put("path", "/" +  dirId + "/" + baseDateDir +"/" + newUUID  + sufName);

                        }
                        else {
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
                            json.put("path", "/" +  dirId + "/" + baseDateDir + "/" + uuid  + sufName);
                        }
                        if("nr_object".equals(dirId)){
                            int num = oldFileName.lastIndexOf(".");
                            json.put("type", oldFileName.substring(num-1,num));
                        }
                        //如果上传的文件是解译数据，则解压到动态工作空间
                        String shpName = null;
                        String ppath="";
                        if("nr_temp".equals(dirId)){
                            if(".rar".equals(sufName.toLowerCase())){
                                UnRARUtil.unRarByCmd(config.getUpPath() +"/" +  dirId + "/" + baseDateDir + "/" + newUUID  + sufName,config.getUpPath() + "/dynamicLayerSpace");
                                //shpName = UnRARUtil.unRarArcGISFiles(config.getUpPath() +"/" +  dirId + "/" + baseDateDir + "/" + uuid  + sufName ,config.getUpPath() + "/dynamicLayerSpace",newUUID);
                            }else{
                                Date nowDate = new Date();
                                SimpleDateFormat sdf1 =  new SimpleDateFormat("yyyy-MM-dd-HH-mm");
                                //在服务器上压缩后存储的路径
                                String savePath = config.getUpPath() + "/dynamicLayerSpace/"+sdf1.format(nowDate);

                                shpName = ZipUtil.unZipArcGISFiles(config.getUpPath() +"/" +  dirId + "/" + baseDateDir + "/" + newUUID  + sufName,savePath,newUUID);

                                shpName=config.getUpPath() + "/dynamicLayerSpace//"+shpName;
                                //下面是shp文件的在磁盘上的路径但是不是shp后缀的
                                //String url = FTPUtilUtil.getRandomPath(PATH+"/epr/image/","x.shp");
//                                String u = shpName.split("\\:")[1];
//                                String ftpPath = u.split("\\.")[0];
//                                ftpPath = ftpPath.substring(0,u.lastIndexOf("/"))+"/";
//                                String name = u.substring(u.lastIndexOf("/")+1);
//                                name = name.split("\\.")[0];


                                String fileName="";
                                FileInputStream input=null;
                                File saveDir = new File(savePath);

                                if(saveDir.isDirectory()){
                                    File[] filess = saveDir.listFiles();
                                    for (File filesing:filess) {
                                        //SHP上传到GIS服务器
                                        String url = filesing.getAbsolutePath();//D:\epr\UploadData\dynamicLayerSpace\1.img
                                        String u = url.split("\\:")[1];//\epr\UploadData\dynamicLayerSpace\1.img
                                        String ftpPath ="/dynamicSpace/";
                                        String name = filesing.getName();
                                        String suf  =filesing.getName().substring(filesing.getName().lastIndexOf(".")+1);
//                                        name = filesing.getName();
                                        shpName=filesing.getName();
                                        if("img".equals(suf)){
                                            fileName = name;
                                            input = new FileInputStream(new File(url));
                                        }else if("rrd".equals(suf)){
                                            fileName = name ;
                                            input = new FileInputStream(new File(url));
                                        }
                                        else if("enp".equals(suf)){
                                            fileName = name ;
                                            input = new FileInputStream(new File(url));
                                        }
                                        else if("xml".equals(suf)){
                                            fileName = name ;
                                            input = new FileInputStream(new File(url));
                                        }else if("tif".equals(suf)){
                                            fileName = name ;
                                            input = new FileInputStream(new File(url));
                                        }
                                            //FTPUtilUtil ftp = new FtpUtils22();
                                        FTPUtilUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName, input);
                                            if("tif".equals(suf)||"img".equals(suf)){
                                                ppath = ftpPt+ftpUrl+ftpPath+fileName;
                                            }

                                    }
                                }


                            }
                            json.put("path", ppath);
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
