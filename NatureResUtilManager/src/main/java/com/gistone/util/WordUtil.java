package com.gistone.util;

import cn.afterturn.easypoi.entity.ImageEntity;
import cn.afterturn.easypoi.word.WordExportUtil;
import org.apache.poi.xwpf.usermodel.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class WordUtil {

    /**
     * 模板生成Word(允许图片)
     * @param path  模板路径  marker.docx
     * @param docxPath  保存路径
     * @param name      保存文件名
     * @param params    替换数据格式 看模板注释
     * @param pictureMap   key 图片模板标识   value 图片全路径
     */
    public static boolean exportWord(String path, String docxPath, String name,Map<String,Object> params, Map<String,String> pictureMap) {
        BufferedInputStream in = null;
        ByteArrayOutputStream output = null;
        FileOutputStream fos = null;
        try {
            //图片处理
            if(null!=pictureMap){
                for (Map.Entry<String,String> entry : pictureMap.entrySet()) {
                    //获取图片附件
                    File file = new File(entry.getValue());
                    in = new BufferedInputStream(new FileInputStream(file));
                    //设置图片比例高度
                    BufferedImage sourceImg = ImageIO.read(new FileInputStream(file));
                    double proportion = (double) sourceImg.getHeight() / sourceImg.getWidth();
                    double height = proportion * 500;
                    System.out.println(new Double(height).intValue());
                    output = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len;
                    while((len = in.read(buffer)) > 0)
                        output.write(buffer,0, len);
                    ImageEntity image = new ImageEntity();
                    image.setWidth(500); //宽度
                    image.setHeight(new Double(height).intValue()); //比例高度
                    image.setUrl(entry.getValue()); //图片原路径
                    image.setData(output.toByteArray()); //图片数据
                    image.setType(ImageEntity.Data);
                    params.put(entry.getKey(),image);
                }
            }
            //写入模板
            XWPFDocument doc = WordExportUtil.exportWord07(path,params);
            //创建文件夹
            File f1 = new File(docxPath);
            if(!f1.isDirectory()) f1.mkdirs();
            fos = new FileOutputStream(docxPath+name);
            doc.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if(null!=output){
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null!=in){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null!=fos){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
    }







}
