package com.gistone.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author zf1017@foxmail.com
 * @date 2019/7/22 0022 11:34
 * @description
 */
@Slf4j
public class FTPUtil {
    /**
     * 获取FTPClient对象
     *
     * @param ftpHost     FTP主机服务器
     * @param ftpPassword FTP 登录密码
     * @param ftpUserName FTP登录用户名
     * @param ftpPort     FTP端口 默认为21
     * @return
     */
    public static FTPClient getFTPClient(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient = new FTPClient();
            if(!ftpClient.isConnected()){
                ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
            }

            ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                log.info("未连接到FTP，用户名或密码错误。");
                ftpClient.disconnect();
            } else {
//                log.info("FTP连接成功。");
            }
        } catch (SocketException e) {
            e.printStackTrace();
            log.info("FTP的IP地址可能错误，请正确配置。");
        } catch (IOException e) {
            e.printStackTrace();
            log.info("FTP的端口错误,请正确配置。");
        }
        return ftpClient;
    }

    /**
     * Title: judgeFtpConnect
     * Description: 判断ftp连接是否成功
     *
     * @param ftpHost
     * @param ftpUserName
     * @param ftpPassword
     * @param ftpPort
     * @return
     */
    public static int judgeFtpConnect(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
            ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                ftpClient.disconnect();
                return 0;
            } else {
                return 1;
            }
        } catch (SocketException e) {
            return 0;
        } catch (IOException e) {
            return 0;
        }
    }

    /**
     * 下载文件
     *
     * @param ftpHost     ftp服务器地址
     * @param ftpUserName anonymous匿名用户登录，不需要密码。administrator指定用户登录
     * @param ftpPassword 指定用户密码
     * @param ftpPort     ftp服务员器端口号
     * @param ftpPath     ftp文件存放物理路径
     * @param fileName    文件路径
     */
    public static String downloadFile(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort,
                                      String ftpPath, String localPath, String fileName) {
        String isItDone = "";
        FTPClient ftpClient = null;
        try {
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
//            ftpClient.setControlEncoding("UTF-8"); // 中文支持
//            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            //应对中文目录
            ftpPath = new String(ftpPath.getBytes("GBK"),"iso-8859-1");
            ftpClient.changeWorkingDirectory(ftpPath);// 转移到FTP服务器目录
            FTPFile[] fs = ftpClient.listFiles();
            for (FTPFile ff : fs) {
                String fname = new String(ff.getName().getBytes("iso-8859-1"), "gbk");
                if (fname.equals(fileName)) {
                    File newFile = new File(localPath);
                    if(newFile.exists()==false){
                        newFile.mkdirs();
                    }
                    File localFile = new File(localPath + File.separatorChar + fileName);
                    OutputStream os = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(ff.getName(), os);
                    os.close();
                    break;
                }
            }
            ftpClient.logout();
            isItDone = "0";
        } catch (FileNotFoundException e) {
            isItDone = "没有找到" + ftpPath + "文件。";
            e.printStackTrace();
        } catch (SocketException e) {
            isItDone = "连接FTP失败。";
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            isItDone = "文件读取错误。";
            e.printStackTrace();
        }
        return isItDone;
    }






    /**
     * 上传文件
     *
     * @param ftpHost     ftp服务器地址
     * @param ftpUserName anonymous匿名用户登录，不需要密码。administrator指定用户登录
     * @param ftpPassword 指定用户密码
     * @param ftpPort     ftp服务员器端口号
     * @param ftpPath     ftp文件存放物理路径
     * @param fileName    文件路径
     * @param input       文件输入流，即从本地服务器读取文件的IO输入流
     */
    public static String uploadFile(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort,
                                    String ftpPath, String fileName, InputStream input) {
        String isItDone = "";
        FTPClient ftp = null;
        try {
            String ftpPaths = new String(ftpPath.getBytes("GBK"), "iso-8859-1");
            ftp = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            if (!ftp.changeWorkingDirectory(ftpPath)) {// 判断目录是否存在，不存在，则创建
                ftp.makeDirectory(ftpPaths);
            }
            ftp.enterLocalPassiveMode();
            ftp.changeWorkingDirectory(ftpPaths);
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            fileName = new String(fileName.getBytes("GBK"), "iso-8859-1");
            ftp.storeFile(fileName, input);
            input.close();
            ftp.logout();
            isItDone = "0";
        } catch (UnsupportedEncodingException e) {
            isItDone = "ftp连接错误。";
            e.printStackTrace();
        } catch (IOException e) {
            if (e.getMessage().equals("Connection is not open")) {
                isItDone = "ftp打开连接失败。";
            } else {
                isItDone = "ftp上传错误。";
            }
            e.printStackTrace();
        }
//        log.info("success");
        return isItDone;
    }

    public static boolean createDri(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort, String ftpPath) {
        FTPClient ftp;
        try {
            String ftpPaths = new String(ftpPath.getBytes("UTF-8"), "UTF-8");
            ftp = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            if (!ftp.changeWorkingDirectory(ftpPath)) {// 判断目录是否存在，不存在，则创建
                return ftp.makeDirectory(ftpPaths);
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return false;
    }

    public static boolean isDri(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort, String ftpPath) {
        FTPClient ftp;
        try {
            String ftpPaths = new String(ftpPath.getBytes("UTF-8"), "UTF-8");
            ftp = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            if (ftp.changeWorkingDirectory(ftpPath)) {// 判断目录是否存在，不存在，则创建
                return true;
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return false;
    }


    /**
     * <删除FTP上的文件>
     * <远程删除FTP服务器上的录音文件>
     *
     * @param url        FTP服务器IP地址
     * @param port       FTP服务器端口
     * @param username   FTP服务器登录名
     * @param password   FTP服务器密码
     * @param remotePath 远程文件路径
     * @param fileName   待删除的文件名
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String deleteFtpFile(String url, int port, String username, String password, String remotePath, String fileName) {
        String success = "";
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            // 连接FTP服务器  
            if (port > -1) {
                ftp.connect(url, port);
            } else {
                ftp.connect(url);
            }
            // 登录  
            ftp.login(username, password);
            ftp.setControlEncoding("GBK");
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            // 转移到FTP服务器目录  
            remotePath = new String(remotePath.getBytes("GBK"), "iso-8859-1");
            ftp.changeWorkingDirectory(remotePath);
            fileName = new String(fileName.getBytes("GBK"), "iso-8859-1");
            boolean isdel = ftp.deleteFile(fileName);
            if (isdel) {
                success = "0";
            } else {
                success = "删除ftp失败";
            }
            ftp.logout();
        } catch (IOException e) {
            success = "删除ftp错误" + e;
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    success = "删除ftp错误" + e;
                }
            }
        }
        return success;
    }




    /**
     * 获取FTP某一特定目录下的所有文件名称
     * @param ftpDirPath    FTP上的目标文件路径
     */
    public static List<String> getFileNameListFromFTP(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort, String ftpDirPath) {
        List<String> fileNames = new ArrayList<>();
        FTPClient ftpClient = null;
        try {
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            ftpDirPath = new String(ftpDirPath.getBytes("GBK"),"iso-8859-1");
            if (ftpDirPath.startsWith("/") && ftpDirPath.endsWith("/")) {
                // 通过提供的文件路径获取FTPFile对象列表

                FTPFile[] files = ftpClient.listFiles(ftpDirPath);
                // 遍历文件列表，打印出文件名称
                for (int i = 0; i < files.length; i++) {
                    FTPFile ftpFile = files[i];
                    // 此处只打印文件，未遍历子目录（如果需要遍历，加上递归逻辑即可）
                    if (ftpFile.isFile()) {
                        fileNames.add(ftpFile.getName());
                        log.info(ftpDirPath + ftpFile.getName());
                    }
                }
            } else {
                log.error("当前FTP路径不可用");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileNames;
    }

    public static void main(String[] args) throws IOException {
        String ftpHost = "120.24.69.160"; // ftp服务器地址
        int ftpPort = 22;// ftp服务员器端口号
        String ftpUserName = "ceshi";// anonymous匿名用户登录，不需要密码。administrator指定用户登录
        String ftpPassword = "123456";// 指定用户密码
		String ftpPath = "/xxx/"; // ftp文件存放物理路径
		String filePath = "D:/risk/123-测试/"; // 文件路径
		String fileName = "123.zip";// 文件名称

//        List<String> fileNameListFromFTP = FTPUtil.getFileNameListFromFTP(ftpHost, ftpUserName, ftpPassword, ftpPort, "/ftp/risk/input/测试2-97/20190723/");
//        fileNameListFromFTP.forEach(System.out::println);
//		
//		System.out.println(StringUtils.substringAfter("/dsmFile/triggerZip/20180920111722", "/"+".zip"));
//		
        //int a=judgeFtpConnect(ftpHost, ftpUserName, ftpPassword, ftpPort);
        //System.out.println(a);
//		filePath = "d:/log";
//		fileName = "aaa.zip";
       FTPUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, filePath, fileName);

//		filePath = "D:\\dsmFile\\emergency\\month";
//		fileName = "月运维汇总2018.07.30-11.55.docx";
		FileInputStream input = new FileInputStream(new File(filePath + File.separatorChar + fileName));
		FTPUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName, input);

        //System.out.println(FtpUtils.deleteFtpFile(ftpHost, ftpPort, ftpUserName, ftpPassword, "2018年/2018年1月/周运维/大厅使用日志", "aaa.txt"));
//		BigDecimal decimal=new BigDecimal(140);
//		if(new BigDecimal(decimal.intValue()).compareTo(decimal)==0){
//			System.out.println(decimal.toString());
//		}else {
//		System.out.println(decimal.setScale(2,BigDecimal.ROUND_HALF_UP).stripTrailingZeros());}


//		//得到图片缓冲区 
//		BufferedImage bi = new BufferedImage
//
//		(150,70,BufferedImage.TYPE_INT_RGB);//INT精确度达到一定,RGB三原色，高度70,宽度150
//
//		//得到它的绘制环境(这张图片的笔) 
//		Graphics2D g2 = (Graphics2D) bi.getGraphics();
//
//		g2.fillRect(0,0,150,70);//填充一个矩形 左上角坐标(0,0),宽70,高150;填充整张图片 
//		//设置颜色 
//		g2.setColor(Color.WHITE); 
//		g2.fillRect(0,0,150,70);//填充整张图片(其实就是设置背景颜色)
//
//		g2.setColor(Color.RED); 
//		g2.drawRect(0,0,150-1,70-1); //画边框
//
//		g2.setFont(new Font("宋体",Font.BOLD,18)); //设置字体:字体、字号、大小 
//		g2.setColor(Color.BLACK);//设置背景颜色
//		g2.drawString("Ⅳ",3,50); //向图片上写字符串 
//		
//
//		ImageIO.write(bi,"JPEG",new FileOutputStream("F:/a.jpg"));//保存图片 JPEG表示保存格式
    }


}

