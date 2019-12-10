package com.gistone.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static com.gistone.util.FileUtil.compress;


public class ZipUtil {

    private static final Log log = LogFactory.getLog(ZipUtil.class);


    /**
     * 压缩文件
     *
     * @param srcfile File[] 需要压缩的文件列表
     * @param zipfile File 压缩后的文件
     */
    public static void zipFiles(List<File> srcfile, File zipfile) {
        byte[] buf = new byte[1024];
        try {
            // Create the ZIP file
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
            // Compress the files
            for (int i = 0; i < srcfile.size(); i++) {
                File file = srcfile.get(i);
                FileInputStream in = new FileInputStream(file);
                // Add ZIP entry to output stream.
                out.putNextEntry(new ZipEntry(file.getName()));
                // Transfer bytes from the file to the ZIP file
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                // Complete the entry
                out.closeEntry();
                in.close();
            }
            // Complete the ZIP file
            out.close();
        } catch (IOException e) {
           log.error("ZipUtil zipFiles exception:"+e);
        }
    }


    public static void main(String[] args) throws FileNotFoundException {
        File zipfile=new File("C:\\Users\\EDZ\\Desktop\\安徽红线\\前端李帅\\yss_dist.zip");
//        srcfile.add(new File("e:\\2.xls"));
//        srcfile.add(new File("e:\\3.xls"));
//        srcfile.add(new File("e:\\4.xls"));
//        srcfile.add(new File("e:\\5.xls"));

        String dirfile = "D:\\1jieya";
        ZipUtil.toZip( "D:\\epr\\JYResult\\f5d97097c39345519137ac21f1abd0fc","D:\\epr\\JYResult" , "D:\\epr\\JYResult\\f5d97097c39345519137ac21f1abd0fc.zip",true);
    }
    /**
     * 解压缩
     *
     * @param zipfile File 需要解压缩的文件
     * @param descDir String 解压后的目标目录
     */
    public static void unZipFiles(File zipfile, String descDir) {
        try {
            // Open the ZIP file
            ZipFile zf = new ZipFile(zipfile);
            for (Enumeration entries = zf.entries(); entries.hasMoreElements();) {
                // Get the entry name
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String zipEntryName = entry.getName();
                InputStream in = zf.getInputStream(entry);
                // System.out.println(zipEntryName);
                OutputStream out = new FileOutputStream(descDir + zipEntryName);
                byte[] buf1 = new byte[1024];
                int len;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
                // Close the file and stream
                in.close();
                out.close();
            }
        } catch (IOException e) {
            log.error("ZipUtil unZipFiles exception:"+e);
        }
    }

 public static String unZipArcGISFiles(String zipFileStr, String descDir, String uuidStr) throws IOException {

	 	//返回动态工作空间的文件名，tif文件或img文件
	 	String fileUrl = null;

	 	//1.创建解压文件对象
		File zipFile = new File(zipFileStr);
		String zipEntryName = null;
		if(!zipFile.exists()){	//如果文件不存在
			return null;
		}
		ZipFile zip = new ZipFile(zipFile,Charset.forName("GBK"));//解决中文文件夹乱码

		//2.开始解压文件
       for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements();) {
           ZipEntry entry = (ZipEntry) entries.nextElement();

           //如果是文件，才解压到arcgis工作空间中
           if(entry.isDirectory()){
        	   continue;
           }

           //3.生成解压后的文件路径
           zipEntryName = entry.getName();
           zipEntryName = zipEntryName.substring(zipEntryName.lastIndexOf("/")+1);
           String outPath = (descDir+"/"+ uuidStr + zipEntryName.substring(zipEntryName.lastIndexOf("."))).replaceAll("\\*", "/");
           // 判断路径是否存在,不存在则创建文件路径
           File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
           if (!file.exists()) {
               file.mkdirs();
           }
           // 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
           if (new File(outPath).isDirectory()) {
               continue;
           }
           FileOutputStream out = new FileOutputStream(outPath);

           //4.如果是tif文件或img文件，则返回文件名称
           String shapeName = zipEntryName.substring(zipEntryName.lastIndexOf("."));
           if(shapeName.equals(".tif") || shapeName.equals(".img") ){
        	   //fileUrl = uuidStr +"_"+ zipEntryName; //文件名称太长导致gp服务失败
        	   fileUrl = uuidStr;
           }

           //5.复制文件
           InputStream in = zip.getInputStream(entry);
           byte[] buf1 = new byte[1024];
           int len;
           while ((len = in.read(buf1)) > 0) {
               out.write(buf1, 0, len);
           }
           in.close();
           out.close();
       }
		return fileUrl;

    }
    /**
     * 压缩成ZIP 方法1
     * @param srcDir 压缩文件夹路径
     * @param  outFile 压缩输出文件路径
     * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构;
     *                          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(String srcDir, String outDir, String outFile , boolean KeepDirStructure)
            throws RuntimeException, FileNotFoundException {
        long start = System.currentTimeMillis();
        File fi = new File(outFile);
        OutputStream out = new FileOutputStream(fi);
        ZipOutputStream zos = null ;
        try {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            compress(sourceFile,zos,sourceFile.getName(),KeepDirStructure);
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) +" ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils",e);
        }finally{
            if(zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
        /**
         * 压缩成ZIP 方法2
         * @param srcFiles 需要压缩的文件列表
         * @param out           压缩文件输出流
         * @throws RuntimeException 压缩失败会抛出运行时异常
         */
    public static void toZip(List<File> srcFiles , OutputStream out)throws RuntimeException {
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null ;
        try {
            zos = new ZipOutputStream(out);
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[1024];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1){
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            }
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) +" ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils",e);
        }finally{
            if(zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
//
    /**
     * Main
     *
     * @param args
     */

}
