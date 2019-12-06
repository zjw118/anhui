package com.gistone.util;


import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.UUID;

/**
 * 上传图片
 * @author xjc
 */
public class PictureUtils {

    /**
     * 得到上传图片路径
     *
     * @param path 路径
     * @param file 格式
     * @return
     */
    public static String getPicturePath(String path, MultipartFile file) {

        String filePath = null;

        if (file == null) {
            filePath = null;
        } else {

            String format1 = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());
            String excelName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + format1;
            String nowTime = DateUtils.format(new Date(), "yyyyMMdd");

            if (!file.isEmpty()) {

                InputStream in = null;
                OutputStream out = null;
                OutputStream out2 = null;

                try {
                    int num = 0;   //文件夹，0 Excel，1 文档，2 PDF
                    String format = excelName.substring(excelName.lastIndexOf(".") + 1);
                    if (format.equals("xls") || format.equals("xlsx")) {
                        num = 0;
                    } else if (format.equals("docx")) {
                        num = 1;
                    } else if (format.equals("pdf")) {
                        num = 2;
                    } else if (format.equals("txt")) {
                        num = 3;
                    } else if (format.equals("jpg") || format.equals("png") || format.equals("jpeg") || format.equals("gif")) {
                        num = 4;
                    } else {
                        num = 5;
                    }

                    File dir = new File(path);
                    if (!dir.exists())
                        dir.mkdirs();
                    String[] split = excelName.split("\\.");
                    String key = UUID.randomUUID().toString().replaceAll("-", "");

                    String name = split[0] + "_" + key + "." + split[1];
                    filePath = path + nowTime + "/" + num + "/" + name;

                    File serverFile = new File(filePath);
                    in = file.getInputStream();
                    //判断文件夹是否存在，不存在则创建
                    File excelPathFile = new File(path + File.separator + nowTime + "/" + num + "/");
                    if (!excelPathFile.exists() && !excelPathFile.isDirectory()) {
                        excelPathFile.mkdirs();
                    }
                    out = new FileOutputStream(serverFile);
                    byte[] b = new byte[1024];
                    int len = 0;
                    while ((len = in.read(b)) > 0) {
                        out.write(b, 0, len);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                            out = null;
                        }
                        if (out2 != null) {
                            out2.close();
                            out2 = null;
                        }

                        if (in != null) {
                            in.close();
                            in = null;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return filePath;
    }

    public static void uploadFile(String path, MultipartFile file) {


        if (!file.isEmpty()) {
            InputStream in = null;
            OutputStream out = null;
            OutputStream out2 = null;
            try {
                File serverFile = new File(path);
                in = file.getInputStream();

                out = new FileOutputStream(serverFile);
                byte[] b = new byte[1024];
                int len = 0;
                while ((len = in.read(b)) > 0) {
                    out.write(b, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                        out = null;
                    }
                    if (out2 != null) {
                        out2.close();
                        out2 = null;
                    }

                    if (in != null) {
                        in.close();
                        in = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
