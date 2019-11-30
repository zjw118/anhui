
package com.gistone.util;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class Word2Pdf {

    static final int wdFormatPDF = 17;// PDF 格式
    static final Integer PPT_TO_PDF_OPERAND = 32;//ppt
    static final Integer EXCEL_TO_PDF_OPERAND = 0;//excel

    //
    public static int Word2Pdf(String sfileName, String toFileName) {

        System.out.println("启动Word...");
        long start = System.currentTimeMillis();
        ActiveXComponent app = null;
        Dispatch doc = null;
        try {
            app = new ActiveXComponent("Word.Application");
            app.setProperty("Visible", new Variant(false));
            Dispatch docs = app.getProperty("Documents").toDispatch();
            doc = Dispatch.call(docs, "Open", sfileName).toDispatch();
            System.out.println("打开文档..." + sfileName);
            System.out.println("转换文档到PDF..." + toFileName);
            File tofile = new File(toFileName);
            if (tofile.exists()) {
                tofile.delete();
            }
            Dispatch.call(doc,
                    "SaveAs",
                    toFileName, // FileName
                    EXCEL_TO_PDF_OPERAND);
            long end = System.currentTimeMillis();
//            System.out.println("转换完成..用时：" + (end - start) + "ms.");


        } catch (Exception e) {
            System.out.println("========Error:文档转换失败：" + e.getMessage());
            return 0;
        } finally {
            Dispatch.call(doc, "Close", false);
//            System.out.println("关闭文档");
            if (app != null)
                app.invoke("Quit", new Variant[]{});
        }
        //如果没有这句话,winword.exe进程将不会关闭
        ComThread.Release();
        return 1;
    }

    public static void Ppt2Pdf(String srcFilePath, String pdfFilePath) throws Exception {
        ActiveXComponent app = null;
        Dispatch ppt = null;
        try {
            ComThread.InitSTA();
            app = new ActiveXComponent("PowerPoint.Application");
            Dispatch ppts = app.getProperty("Presentations").toDispatch();


            /*
             * call
             * param 4: ReadOnly
             * param 5: Untitled指定文件是否有标题
             * param 6: WithWindow指定文件是否可见
             * */

            ppt = Dispatch.call(ppts, "Open", srcFilePath, true, true, false).toDispatch();
            Dispatch.call(ppt, "SaveAs", pdfFilePath, PPT_TO_PDF_OPERAND); // ppSaveAsPDF为特定值32

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (ppt != null) {
                Dispatch.call(ppt, "Close");
            }
            if (app != null) {
                app.invoke("Quit");
            }
            ComThread.Release();
        }
    }

    public static void Excel2Pdf(String inFilePath, String outFilePath) throws Exception {
        ActiveXComponent ax = null;
        Dispatch excel = null;
        try {
            ComThread.InitSTA();
            ax = new ActiveXComponent("Excel.Application");
            ax.setProperty("Visible", new Variant(false));
            ax.setProperty("AutomationSecurity", new Variant(3)); // 禁用宏
            Dispatch excels = ax.getProperty("Workbooks").toDispatch();

            Object[] obj = new Object[]{
                    inFilePath,
                    new Variant(false),
                    new Variant(false)
            };
            excel = Dispatch.invoke(excels, "Open", Dispatch.Method, obj, new int[9]).toDispatch();

            // 转换格式
            Object[] obj2 = new Object[]{
                    new Variant(EXCEL_TO_PDF_OPERAND), // PDF格式=0
                    outFilePath,
                    new Variant(0)  //0=标准 (生成的PDF图片不会变模糊) ; 1=最小文件
            };
            Dispatch.invoke(excel, "ExportAsFixedFormat", Dispatch.Method, obj2, new int[1]);

        } catch (Exception es) {
            es.printStackTrace();
            throw es;
        } finally {
            if (excel != null) {
                Dispatch.call(excel, "Close", new Variant(false));
            }
            if (ax != null) {
                ax.invoke("Quit", new Variant[]{});
                ax = null;
            }
            ComThread.Release();
        }

    }

   /* public static boolean officeFileConverterToPdf(String argInputFilePath, String argPdfPath) {
        if (argInputFilePath.isEmpty() || argPdfPath.isEmpty() || getFileSufix(argInputFilePath).isEmpty()) {
            log.debug("输入或输出文件路徑有誤！");
            return false;
        }

        String suffix = getFileSufix(argInputFilePath);

        File file = new File(argInputFilePath);
        if (!file.exists()) {
            log.debug("文件不存在！");
            return false;
        }

        // PDF如果不存在则创建文件夹
        file = new File(getFilePath(argPdfPath));
        if (!file.exists()) {
            file.mkdir();
        }

        // 如果输入的路径为PDF 则生成失败
        if (suffix.equals("pdf")) {
            System.out.println("PDF not need to convert!");
            return false;
        }

        if (suffix.equals("doc") || suffix.equals("docx") || suffix.equals("txt")) {
            return wordToPDF(argInputFilePath, argPdfPath);
        } else if (suffix.equals("xls") || suffix.equals("xlsx")) {
            return excelToPdf(argInputFilePath, argPdfPath);
        } else if (suffix.equals("ppt") || suffix.equals("pptx")) {
            return pptToPdf(argInputFilePath, argPdfPath);
            // return ppt2PDF(argInputFilePath, argPdfPath);
        }

        return false;
    }
*/
    /**
     *  * get file extension
     *  *
     *  * @param argFilePath
     *  * @return
     *  
     */
    public static String getFileSufix(String argFilePath) {
        int splitIndex = argFilePath.lastIndexOf(".");
        return argFilePath.substring(splitIndex + 1);
    }

    /**
     *  * subString file path
     *  *
     *  * @param argFilePath
     *  *   file path
     *  * @return filePaths
     *  
     */
    public static String getFilePath(String argFilePath) {
        int pathIndex = argFilePath.lastIndexOf("/");
        return argFilePath.substring(0, pathIndex);
    }





}

