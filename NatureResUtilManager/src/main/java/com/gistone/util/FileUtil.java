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
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
     * 创建打散目录
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
        return null;
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
     * 上传单个附件（前端标识 file）允许附件为空
     * @param request
     * @param path  完整目录
     * @param arr    扩展名  String[] arr = {"xxx",""};
     * @param size    附件大小  1MB = 1000 000
     * @return  Map    正常返回 oldName,newName  错误返回说明 error
     */
    public static Map<String,String> uploadFile(HttpServletRequest request, String path,String[] arr, Long size){
        Map<String,String> map = null;
        try {
            map = new HashMap();
            //创建目录
            File f = new File(path);
            if(!f.isDirectory()) f.mkdirs();
            if(!ServletFileUpload.isMultipartContent(request)) return null;
            MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
            MultipartFile file = multipartRequest.getFile("file");
            if(null==file){
                return null;
            }
            //判断附件格式
            String oldName = file.getOriginalFilename();
            String suffix = oldName.substring(oldName.lastIndexOf("."));
            boolean b = Arrays.asList(arr).contains((suffix.split("\\."))[1]);
            if(!b){
                map.put("error","附件格式错误");
                return map;
            }
            //判断附件大小
            if(size<file.getSize()){
                map.put("error","附件超出大小限制");
                return map;
            }
            //生成附件名称
            SimpleDateFormat ymdhms = new SimpleDateFormat("yyyyMMdd-HHmmss");
            String newName = ymdhms.format(new Date())+UUID.randomUUID().toString().substring(23,36)+oldName.substring(oldName.lastIndexOf("."));
            file.transferTo(new File(path,newName));
            map.put("oldName",oldName);
            map.put("newName",newName);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error","上传失败");
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



    /**
     * 写入附件
     * @param path   路径
     * @param name   文件名称
     * @param content   数据
     * @return
     */
    public static boolean writeInFile(String path,String name, String content) {
        Writer writer = null;
        Boolean b = false;
        try {
            //创建文件夹
            File f1 = new File(path);
            if(!f1.isDirectory()) f1.mkdirs();
            //创建文件
            if(!path.endsWith("/")) path += "/";
            File f2 = new File(path+name);
            if(!f2.exists()) f2.createNewFile();
            StringBuilder outputString = new StringBuilder();
            outputString.append(content + "\r\n");
            writer = new FileWriter(f2, true);
            writer.write(outputString.toString());
            b = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(null!=writer)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return b;
        }
    }


    /**
     * 附件读取-字符流
     * @param path  路径
     * @return
     * @throws IOException
     */
    public static String readFromTextFile(String path) {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            BufferedReader breader = new BufferedReader(new FileReader(path));
            String temp;
            while (null!=(temp=breader.readLine())) {
                stringBuffer.append(temp);
            }
            return stringBuffer+"";
        } catch (Exception e) {
//            e.printStackTrace();
            return "";
        }
    }



    /**
     * 压缩ZIP
     * @param srcDir 压缩文件夹路径
     * @param file  new File("d:/xxx.zip"));
     * @param KeepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     *                        false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException
     */
    public static boolean toZip(String srcDir, File file, boolean KeepDirStructure){
        boolean isItDone = true;
        ZipOutputStream zos = null;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            compress(sourceFile, zos, sourceFile.getName(), KeepDirStructure);
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    isItDone = false;
                    e.printStackTrace();
                }
            }
            if(null!=out){
                try {
                    out.close();
                } catch (IOException e) {
                    isItDone = false;
                    e.printStackTrace();
                }
            }
        }
        return isItDone;
    }
    static void compress(File sourceFile, ZipOutputStream zos, String name, boolean KeepDirStructure) throws Exception {
        byte[] buf = new byte[2048];
        if (sourceFile.isFile()) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if (KeepDirStructure) {
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    zos.closeEntry();
                }
            } else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        compress(file, zos, name + "/" + file.getName(), KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(), KeepDirStructure);
                    }
                }
            }
        }
    }



    /**
     * 下载附件-字节流
     * @param response
     * @param path 附件全路径  D:/xxx.txt
     * @param name 附件回显名称  xxx.txt
     */
    public static boolean downFile(HttpServletResponse response, String path, String name){
        InputStream in = null;
        OutputStream out = null;
        Boolean b = true;
        try {
//            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(name,"UTF-8"));
            response.setContentType("multipart/form-data"); //自动判断下载文件类型
            in = new BufferedInputStream(new FileInputStream(new File(path)));
            int len = 0;
            byte[] buffer = new byte[1024];
            out = response.getOutputStream();
            while((len = in.read(buffer)) > 0) {
                out.write(buffer,0,len);
            }
            out.flush();
        } catch (Exception e) {
            b = false;
            e.printStackTrace();
        } finally{
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return b;
        }
    }





}
