package com.gistone.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;
import net.lingala.zip4j.core.ZipFile;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by qiang on 2017/8/1.
 */
public class FileUtil {

    /**
    * @Author:renqiang
    * @Param:fileName 要删除的文件名
    * @Description:删除文件，可以是文件或文件夹
    * @Date:14:56 2017/8/1
    */
    public static boolean delete(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                System.out.println("删除文件失败:" + fileName + "不存在！");
                return false;
            } else {
                if (file.isFile())
                    return deleteFile(fileName);
                else
                    return deleteDirectory(fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String inputStream2String(InputStream is) {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static void inputstreamtofile(InputStream ins, File file) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param dir 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = FileUtil.deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = FileUtil.deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }

    public static void writeToTxt(String file, String conent) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(conent+"\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static String readTxtFile(String filePath){
        try {
            String log="";
            String encoding="utf-8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    log+=lineTxt+"\r\n";
                }
                read.close();
                return  log;
            }else{
                System.out.println("找不到指定的文件");
                return  null;
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
            return  null;
        }
    }

    public static void clearInfoForFile(String fileName) {
        File file =new File(fileName);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取图片宽度
     * @param file  图片文件
     * @return 宽度
     */
    public static int getImgWidth(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int ret = -1;
        try {
            is = new FileInputStream(file);
            src = javax.imageio.ImageIO.read(is);
            ret = src.getWidth(null); // 得到源图宽
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }


    /**
     * 获取图片高度
     * @param file  图片文件
     * @return 高度
     */
    public static int getImgHeight(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int ret = -1;
        try {
            is = new FileInputStream(file);
            src = javax.imageio.ImageIO.read(is);
            ret = src.getHeight(null); // 得到源图高
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }




    /**
     * 创建目录并打散
     * @param path  前置路径
     * @return
     */
    public static String getPath(String path){
        if(StringUtils.isNotBlank(path)){
            SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
            if(!path.endsWith("/")) path += "/";
            path = path + ymd.format(new Date())+"/"+new Random().nextInt(20)+"/";
            File ml = new File(path);
            if(!ml.isDirectory()) ml.mkdirs();
            return path;
        }
        return "1";
    }


    /**
     * 递归遍历目录下所有文件全路径
     * @param file
     * @return
     */
    public static List<File> listFiles(File file){
        List<File> fileList = new ArrayList<>();
        if (file.isDirectory()){
            for (File listFile : file.listFiles()) {
                fileList.addAll(listFiles(listFile));
            }
        }else {
            fileList.add(file);
        }
        return fileList;
    }

    public  static void  createFile(String dir){
        File file = new File(dir);
        if(!file.exists()){
            file.mkdirs();
        }
    }
    public static Object confirmWrited(String dir) {
        File file = new File(dir);
        if (file.canWrite()){
            return null;
        }
        return true;
    }



    /**
     * 表单附件上传
     * 必须spring.servlet.multipart.enabled=false 禁用spring附件大小限制
     * 必须post请求
     * 必须multipart/form-data请求类型
     * 表单字段数据只能从此获取
     * @param request
     * @param path  附件保存目录   E:/xxx/xxx
     * @param arr    扩展名  String[] arr = {"xxx",""};
     * @param size    单附件大小  1MB = 1
     * @return  Map    正常返回 fileList[oldName,newName]  +  表单字段  + 错误返回说明 error!="0"
     */
    public static Map<String,Object> uploadFiles(HttpServletRequest request, String path,String[] arr, int size){
        InputStream in = null;
        OutputStream out = null;
        Map<String,Object> map = null;
        try {
            map = new HashMap<>();
            map.put("error","0");
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //判断表单类型
            if (!ServletFileUpload.isMultipartContent(request)) {
                map.put("error","表单类型错误，请使用multipart/form-data");
                return map;
            }
            //设置缓存文件的路径
            File f = new File(path);
            if(!f.isDirectory()) f.mkdirs();
            factory.setRepository(new File(path));
            //设置缓存的大小
            factory.setSizeThreshold(50 * 1024 * 1024);
            //文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("UTF-8");
            //限制单个上传文件大小(M)
            upload.setFileSizeMax(1024 * 1024 * size);
            //限制总上传文件大小(M)
            //upload.setSizeMax(1024 * 1024 * 5);
            List<FileItem> fileItemList = upload.parseRequest(request);
            if (0 < fileItemList.size()) {
                List<Map<String,String>> list = new ArrayList();
                for (FileItem item : fileItemList) {
                    Map<String,String> m = new HashMap();
                    if (item.isFormField()) {
                        //表单
                        map.put(item.getFieldName(),item.getString("UTF-8"));
                    } else {
                        //附件
                        String fileName = item.getName();
                        //处理不同的浏览器提交的附件名不一样的问题
                        fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
                        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
                        boolean b = Arrays.asList(arr).contains(suffix);
                        if(!b){
                            map.put("error","附件格式错误");
                            return map;
                        }
                        //跳过上传输为空的附件
                        if (org.apache.commons.lang.StringUtils.isBlank(fileName)) continue;
                        //附件新名称
                        String newName = UUID.randomUUID().toString()+"."+suffix;
                        if(!path.endsWith("/")) path += "/";
                        //开始上传
                        File srcFile = new File(path + newName);
                        in = item.getInputStream();
                        out = new FileOutputStream(srcFile);
                        byte[] buf = new byte[1024];
                        int length;
                        while ((length = in.read(buf)) != -1) {
                            out.write(buf, 0, length);
                        }
                        m.put("oldName",fileName);
                        m.put("newName",newName);
                        //删除临时文件
                        Thread.sleep(1000);
                        item.delete();
                    }
                    if(0<m.size()) list.add(m);
                }
                map.put("fileList",list);
            }else{
                map.put("error","请求异常");
                return map;
            }
        } catch (FileUploadException e) {
            map.put("error","附件超出大小限制");
        } catch (Exception e) {
            map.put("error","附件上传失败");
            e.printStackTrace();
        } finally {
            try {
                if(null!=in){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(null!=out){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return map;
        }
    }




    /**
     * 解压附件 zip
     * @param zipPath 压缩包全路径  d:/xxx.zip
     * @param path 放置目录
     * @param password  解压密码
     * @throws Exception
     */
    public static void unPackZip(String zipPath,String path,String password){
        try {
            ZipFile zip = new ZipFile(new File(zipPath));
            //zip4j默认用GBK编码去解压,这里设置编码为GBK的
            zip.setFileNameCharset("GBK");
            zip.extractAll(path);
            // 如果解压需要密码
            if (zip.isEncrypted()) {
                zip.setPassword(password);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static ServletFileUpload setExportDemail(DiskFileItemFactory factory , String tem_savePath){
        //设置缓存文件的路径
        factory.setRepository(new File(tem_savePath));
        // 设置缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
        factory.setSizeThreshold(1024 * 1024 * 10);
        // 高水平的API文件上传处理
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 解决上传文件名的中文乱码问题
        upload.setHeaderEncoding("utf-8");
        return upload;
    }



}