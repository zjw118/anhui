package com.gistone.util;

import com.aspose.cells.License;
import com.aspose.cells.Workbook;
import com.aspose.words.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author zf1017@foxmail.com
 * @date 2019/11/28 0028 16:53
 * @description
 */
public class ExcelToPdf {

    private static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = ExcelToPdf.class.getClassLoader().getResourceAsStream("license.xml"); // license.xml应放在..\WebRoot\WEB-INF\classes路径下
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param wordPath 需要被转换的word全路径带文件名
     * @param pdfPath 转换之后pdf的全路径带文件名
     */
    public static void doc2pdf(String wordPath, String pdfPath) {
        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        try {
            long old = System.currentTimeMillis();
            File file = new File(pdfPath); //新建一个pdf文档
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(wordPath); //Address是将要被转化的word文档
            doc.save(os, com.aspose.words.SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            os.close();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒"); //转化用时
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param excelPath 需要被转换的excel全路径带文件名
     * @param pdfPath 转换之后pdf的全路径带文件名
     */
    public static void excel2pdf(String excelPath, String pdfPath) {
        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        try {
            long old = System.currentTimeMillis();
            Workbook wb = new Workbook(excelPath);// 原始excel路径
            FileOutputStream fileOS = new FileOutputStream(new File(pdfPath));
            wb.save(fileOS, com.aspose.cells.SaveFormat.PDF);
            fileOS.close();
            long now = System.currentTimeMillis();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒"); //转化用时
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        //word 和excel 转为pdf
        String filePaths="D:/logs/Java多线程大合集.doc";
        String fileName="zsqexcel78";
        String pdfPath="D:/epr/attached/1/xxx123456.pdf";
        //doc2pdf(filePaths, pdfPath);//filePaths需要转换的文件位置 pdfPath为存储位置
        String excel2pdf="D:/epr/attached/1/项目准入任务列表-20191118.xls";
        excel2pdf(excel2pdf,pdfPath);
    }


}
