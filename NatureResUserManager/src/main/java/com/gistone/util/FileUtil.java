package com.gistone.util;

import java.awt.image.BufferedImage;
import java.io.*;

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
//    public static boolean delete(String fileName) {
//        try {
//            File file = new File(fileName);
//            if (!file.exists()) {
//                System.out.println("删除文件失败:" + fileName + "不存在！");
//                return false;
//            } else {
//                if (file.isFile())
//                    return deleteFile(fileName);
//                else
//                    return deleteDirectory(fileName);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

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
     * @return 目录删除成功返回true，否则返回false
     */
//    public static boolean deleteDirectory(String dir) {
//        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
//        if (!dir.endsWith(File.separator))
//            dir = dir + File.separator;
//        File dirFile = new File(dir);
//        // 如果dir对应的文件不存在，或者不是一个目录，则退出
//        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
//            System.out.println("删除目录失败：" + dir + "不存在！");
//            return false;
//        }
//        boolean flag = true;
//        // 删除文件夹中的所有文件包括子目录
//        File[] files = dirFile.listFiles();
//        for (int i = 0; i < files.length; i++) {
//            // 删除子文件
//            if (files[i].isFile()) {
//                flag = ExcelUtil.deleteFile(files[i].getAbsolutePath());
//                if (!flag)
//                    break;
//            }
//            // 删除子目录
//            else if (files[i].isDirectory()) {
//                flag = ExcelUtil.deleteDirectory(files[i]
//                        .getAbsolutePath());
//                if (!flag)
//                    break;
//            }
//        }
//        if (!flag) {
//            System.out.println("删除目录失败！");
//            return false;
//        }
//        // 删除当前目录
//        if (dirFile.delete()) {
//            System.out.println("删除目录" + dir + "成功！");
//            return true;
//        } else {
//            return false;
//        }
//    }

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


}