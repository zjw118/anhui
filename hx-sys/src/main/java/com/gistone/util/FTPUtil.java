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

        } catch (Exception e) {
            e.printStackTrace();
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
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();  //被动模式
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
            ftpClient.disconnect();// 断开连接
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
     * 上传FTP
     * @param ftpHost 		地址
     * @param ftpUserName 	账号    anonymous匿名用户登录，不需要密码。
     * @param ftpPassword 	密码
     * @param ftpPort 		端口
     * @param ftpPath 		上传ftp存放路径,不包含计算机盘符与ftp设定盘符   /xx/xx/
     * @param fileName 		上传ftp后附件名称
     * @param input 		文件输入流   FileInputStream input = new FileInputStream(new File(url));
     * @return   			正常返回 0
     */
    public static String uploadFile(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort,String ftpPath, String fileName, InputStream input) {
        String isItDone = null;
        FTPClient ftp = null;
        try {
            String ftpPaths=new String(ftpPath.getBytes("GBK"),"iso-8859-1");
            ftp = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            if (!ftp.changeWorkingDirectory(ftpPath)) {// 判断目录是否存在，不存在，则创建
                ftp.makeDirectory(ftpPaths);
            }
            ftp.enterLocalPassiveMode();
            ftp.changeWorkingDirectory(ftpPaths);
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            fileName = new String(fileName.getBytes("GBK"), "iso-8859-1");
            ftp.storeFile(fileName, input);
            isItDone="0";
        } catch (Exception e) {
            isItDone="ftp错误";
            e.printStackTrace();
        }finally{
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                ftp.logout(); // 退出登录
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                ftp.disconnect();// 断开连接
            } catch (IOException e) {
                e.printStackTrace();
            }
            return isItDone;
        }
    }



    /**
     * 创建文件夹
     * @param ftpHost 		地址
     * @param ftpUserName 	账号    anonymous匿名用户登录，不需要密码。
     * @param ftpPassword 	密码
     * @param ftpPort 		端口
     * @param ftpPath		创建ftp路径,不包含计算机盘符与ftp设定盘符   /xx/xx/
     * @return
     */
    public static boolean createDri(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort, String ftpPath) {
        FTPClient ftp = null;
        Boolean b = false;
        try {
            String ftpPaths = new String(ftpPath.getBytes("UTF-8"), "UTF-8");
            ftp = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            if (!ftp.changeWorkingDirectory(ftpPath)) {
                b = ftp.makeDirectory(ftpPaths);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try {
                ftp.logout(); // 退出登录
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                ftp.disconnect();// 断开连接
            } catch (IOException e) {
                e.printStackTrace();
            }
            return b;
        }
    }


    /**
     * 判断是否存在目录
     * @param ftpHost
     * @param ftpUserName
     * @param ftpPassword
     * @param ftpPort
     * @param ftpPath
     * @return
     */
    public static boolean isDri(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort, String ftpPath) {
        FTPClient ftp;
        try {
            ftp = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            if (ftp.changeWorkingDirectory(ftpPath)) {// 判断目录是否存在，不存在，则创建
                return true;
            }
        }catch (Exception e){
//            System.out.println(e.toString());
        }
        return false;
    }


    /**
     * 获取目录下附件数量
     * @param ftpHost 		地址
     * @param ftpUserName 	账号    anonymous匿名用户登录，不需要密码。
     * @param ftpPassword 	密码
     * @param ftpPort 		端口
     * @param ftpPath		路径,不包含计算机盘符与ftp设定盘符   /xx/xx/
     * @return
     */
    public static int isFile(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort, String ftpPath) {
        FTPClient ftp = null;
        int i = 0;
        try {
            ftp = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            FTPFile[] files = ftp.listFiles(ftpPath);
            if (files!=null&&files.length>0) {
                i = files.length;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try {
                if(null!=ftp)
                    ftp.logout(); // 退出登录
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(null!=ftp)
                    ftp.disconnect();// 断开连接
            } catch (IOException e) {
                e.printStackTrace();
            }
            return i;
        }
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






}

