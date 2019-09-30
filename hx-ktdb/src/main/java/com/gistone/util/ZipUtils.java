package com.gistone.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;

public class ZipUtils {
    /**
     * 解压zip文件
     *
     * @param zipFileName     待解压的zip文件路径，例如：c:\\a.zip
     * @param outputDirectory 解压目标文件夹,例如：c:\\a\
     */
    public static String unZip(String zipFileName, String outputDirectory)
            throws Exception {

        org.apache.tools.zip.ZipFile zipFile = new org.apache.tools.zip.ZipFile(zipFileName,"GBK");
        org.apache.tools.zip.ZipEntry zipEntry = null;
        try {
            Enumeration<?> e = zipFile.getEntries();
            createDirectory(outputDirectory, "");
            while (e.hasMoreElements()) {
                zipEntry = (org.apache.tools.zip.ZipEntry) e.nextElement();
                if (zipEntry.isDirectory()) {
                    String name = zipEntry.getName();
                    name = name.substring(0, name.length() - 1);
                    File f = new File(outputDirectory + File.separator + name);
                    f.mkdir();
                } else {
                    String fileName = zipEntry.getName();
                    fileName = fileName.replace('\\', '/');
                    if (fileName.indexOf("/") != -1) {
                        createDirectory(outputDirectory, fileName.substring(0, fileName.lastIndexOf("/")));
                        fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
                    }
                    File f = new File(outputDirectory + File.separator + zipEntry.getName());
                    f.createNewFile();
                    InputStream in = zipFile.getInputStream(zipEntry);
                    FileOutputStream out = new FileOutputStream(f);

                    byte[] by = new byte[1024];
                    int c;
                    while ((c = in.read(by)) != -1) {
                        out.write(by, 0, c);
                    }
                    in.close();
                    out.close();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            zipFile.close();
        }
        return outputDirectory+"\\"+zipEntry.getName();
    }

    /**
     * 创建目录
     *
     * @param directory
     * @param subDirectory
     * @author hezhao
     * @Time 2017年7月28日 下午7:10:05
     */
    private static void createDirectory(String directory, String subDirectory) {
        String dir[];
        File fl = new File(directory);
        try {
            if (subDirectory == "" && fl.exists() != true) {
                fl.mkdir();
            } else if (subDirectory != "") {
                dir = subDirectory.replace('\\', '/').split("/");
                for (int i = 0; i < dir.length; i++) {
                    File subFile = new File(directory + File.separator + dir[i]);
                    if (subFile.exists() == false)
                        subFile.mkdir();
                    directory += File.separator + dir[i];
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
