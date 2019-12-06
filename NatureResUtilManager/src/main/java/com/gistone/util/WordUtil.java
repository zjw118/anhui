package com.gistone.util;

import cn.afterturn.easypoi.entity.ImageEntity;
import cn.afterturn.easypoi.word.WordExportUtil;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.style.RtfFont;
import org.apache.poi.xwpf.usermodel.*;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

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


    /**
     * 导出word
     * <p>第一步生成替换后的word文件，只支持docx</p>
     * <p>第二步下载生成的文件</p>
     * <p>第三步删除生成的临时文件</p>
     * 模版变量中变量格式：{{foo}}
     * @param templatePath word模板地址
     * @param temDir 生成临时文件存放地址
     * @param fileName 文件名
     * @param params 替换的参数
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    public static void exportWord1(String templatePath, String temDir, String fileName, Map<String, Object> params,
                                  HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull(templatePath,"模板路径不能为空");
        Assert.notNull(temDir,"临时文件路径不能为空");
        Assert.notNull(fileName,"导出文件名不能为空");
        Assert.isTrue(fileName.endsWith(".docx"),"word导出请使用docx格式");
        if (!temDir.endsWith("/")){
            temDir = temDir + File.separator;
        }
        File dir = new File(temDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            String userAgent = request.getHeader("user-agent").toLowerCase();
            if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
            }
            XWPFDocument doc = WordExportUtil.exportWord07(templatePath, params);
            String tmpPath = temDir + fileName;
            FileOutputStream fos = new FileOutputStream(tmpPath);
            doc.write(fos);
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            // 设置文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            OutputStream out = response.getOutputStream();
            doc.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           // delAllFile(temDir);//这一步看具体需求，要不要删
        }

    }



    public static void  SimpleWordExport(String templatePath, String temDir, String fileName, Map<String, Object> params,
                                 HttpServletRequest request, HttpServletResponse response) {

        try {
            XWPFDocument doc = WordExportUtil.exportWord07(templatePath, params);
            FileOutputStream fos = new FileOutputStream(temDir+"/"+fileName);
            doc.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * itext导出word
     * @param map  段落内的替换内容
     * @param list 数据集合
     * @param filePath 文件路径
     * @param num
     */
    public static void getWord(Map<String, Object> map, List<Map> list,
                               String filePath, int num) {
        com.lowagie.text.Document document = new com.lowagie.text.Document(PageSize.A4);
        RtfFont font = new RtfFont("仿宋_GB2312", 12, Font.NORMAL, Color.BLACK);
        try {
            File file = new File(filePath);

            if(!file.exists()){
                File parent = file.getParentFile();
                if(!parent.exists()) {
                    parent.mkdirs();
                }
                try{
                    file.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
            RtfWriter2.getInstance(document, new FileOutputStream(filePath));
            document.open();
            //设置横向显示
            document.setPageSize(PageSize.A4);//document.setPageSize(PageSize.A4.rotate());注释掉的这个是控制横屏展示

            Paragraph ph = new Paragraph("江苏省生态红线问题斑块审核情况表",new RtfFont("黑体", 16, Font.NORMAL,Color.BLACK));
            ph.setAlignment(Element.ALIGN_CENTER);//1是居中对齐
            document.add(ph);
            Font f = new Font();
            //本次共统计全省问题斑块{{n1}}个，审核通过{{n2}}个，审核不通过{{n3}}个，待审核{{n4}}个
            Paragraph p = new Paragraph("本次共统计全省问题斑块"+ map.get("${total}")+ "个，审核通过"+map.get("${t1}")
                    +"个,审核不通过"+map.get("${t2}")+"个,待审核"+map.get("${t3}")+"个。",
                    new RtfFont("黑体", 12, Font.BOLDITALIC,Color.BLACK));
            p.setAlignment(Element.ALIGN_LEFT);//1是居中对齐
            document.add(p);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

            p = new Paragraph(sdf.format(new Date()),
                    new RtfFont("黑体", 14, Font.BOLDITALIC,Color.BLACK));
            p.setAlignment(Element.ALIGN_RIGHT);//是居右对齐

            document.add(p);
            ph.setFont(f);
            Table table = new Table(5);
            int width[] = {17,15,15,15,15};
            table.setWidths(width);
            table.setWidth(100);;
            table.addCell(new Paragraph("行政区划 ", font));
            table.addCell(new Paragraph("斑块数量", font));
            table.addCell(new Paragraph("审核通过数量", font));
            table.addCell(new Paragraph("审核不通过数量", font));
            table.addCell(new Paragraph("待审核数量", font));

            //DecimalFormat df = new DecimalFormat("#.0000");
            for (int i = 0; i < num; i++) {
                table.addCell(new Paragraph(list.get(i).get("COM_NAME") == null ? "" : list.get(i).get("COM_NAME")
                        .toString(), font));
                table.addCell(new Paragraph(list.get(i).get("tzNum")== null ? "" : list.get(i).get("tzNum")
                        .toString(), font));

                table.addCell(new Paragraph(list.get(i).get("emaminedNum") == null ? "" : list.get(i).get("emaminedNum")
                        .toString(), font));
                table.addCell(new Paragraph(list.get(i).get("backNum")== null ? "" : list.get(i).get("backNum")
                        .toString(), font));
                table.addCell(new Paragraph(list.get(i).get("unEmaminedNum")== null ? "" : list.get(i).get("unEmaminedNum")
                        .toString(), font));

            }
//            Cell cell = new Cell(new Paragraph("汇总情况:"+map.get("${allAreaQue}").toString(),font));
//            cell.setColspan(21);
//            cell.setRowspan(1);
//
//            cell.setMaxLines(30);
//            table.addCell(cell);
            table.setWidth(100);
            table.setAlignment(Element.ALIGN_CENTER);// 居中
            table.setAlignment(Element.ALIGN_MIDDLE);// 垂直居中
            document.add(table);
            document.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }
}
