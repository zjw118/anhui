package com.gistone.util;
import java.io.File;

import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.IOException;

import java.io.InputStream;

import java.io.OutputStream;

import java.net.MalformedURLException;

import java.util.List;

import org.apache.commons.net.ftp.FTP;

import org.apache.commons.net.ftp.FTPClient;

import org.apache.commons.net.ftp.FTPFile;

import org.apache.commons.net.ftp.FTPReply;

public class FtpUtils22 {
        // ftp服务器地址

        public String hostname = "10.34.100.135";

    // ftp服务器端口号默认为21

        public Integer port = 21;

    // ftp登录账号

        public String username = "135";

    // ftp登录密码

        public String password = "123456" ;

    public FTPClient ftpClient = null;

    public static void main(String[] args) {
        FtpUtils22 ftp = new FtpUtils22();

        ftp.uploadFile1("\\dynamicSpace\\123\\", "ca5d0a8a0378479e87f421b823e4c155_GF1B_PMS_E117.9_N31.3_20190907_L1A1227691115-PAN_ortho_fuse_clip.img", "D:\\epr\\UploadData\\dynamicLayerSpace\\2019-11-25-18-10\\59e91b0b38214b3fbf6969790bee2f91_GF1B_PMS_E117.9_N31.3_20190907_L1A1227691115-PAN_ortho_fuse_clip.img");
        ftp.uploadFile1("\\dynamicSpace\\123\\", "ca5d0a8a0378479e87f421b823e4c155_GF1B_PMS_E117.9_N31.3_20190907_L1A1227691115-PAN_ortho_fuse_clip.rrd", "D:\\epr\\UploadData\\dynamicLayerSpace\\2019-11-25-18-10\\59e91b0b38214b3fbf6969790bee2f91_GF1B_PMS_E117.9_N31.3_20190907_L1A1227691115-PAN_ortho_fuse_clip.rrd");

        ftp.uploadFile1("\\dynamicSpace\\123\\", "ca5d0a8a0378479e87f421b823e4c155_GF1B_PMS_E117.9_N31.3_20190907_L1A1227691115-PAN_ortho_fuse_clip.img.aux.xml", "D:\\epr\\UploadData\\dynamicLayerSpace\\2019-11-25-18-10\\59e91b0b38214b3fbf6969790bee2f91_GF1B_PMS_E117.9_N31.3_20190907_L1A1227691115-PAN_ortho_fuse_clip.img.aux.xml");
        ftp.uploadFile1("\\dynamicSpace\\123\\", "ca5d0a8a0378479e87f421b823e4c155_GF1B_PMS_E117.9_N31.3_20190907_L1A1227691115-PAN_ortho_fuse_clip.img.enp", "D:\\epr\\UploadData\\dynamicLayerSpace\\2019-11-25-18-10\\59e91b0b38214b3fbf6969790bee2f91_GF1B_PMS_E117.9_N31.3_20190907_L1A1227691115-PAN_ortho_fuse_clip.img.enp");
        ftp.uploadFile1("\\dynamicSpace\\123\\", "ca5d0a8a0378479e87f421b823e4c155_GF1B_PMS_E117.9_N31.3_20190907_L1A1227691115-PAN_ortho_fuse_clip.img.xml", "D:\\epr\\UploadData\\dynamicLayerSpace\\2019-11-25-18-10\\59e91b0b38214b3fbf6969790bee2f91_GF1B_PMS_E117.9_N31.3_20190907_L1A1227691115-PAN_ortho_fuse_clip.img.xml");
//ftp.downloadFile("ftpFile/data", "123.docx", "F://");

//ftp.deleteFile("ftpFile/data", "123.docx");

        System.out.println("okkkkkk");

    }
    public  boolean uploadFileOrgin( String ftpHost, String ftpUserName, String ftpPassword, int ftpPort,String pathname, String fileName, String originfilename) {

        boolean flag = false;

        InputStream inputStream = null;

        try {

            System.out.println("开始上传文件");

            inputStream = new FileInputStream(new File(originfilename));

            initFtpClient();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            CreateDirecroty(pathname);

            ftpClient.makeDirectory(pathname);

            ftpClient.changeWorkingDirectory(pathname);



            ftpClient.storeFile(fileName, inputStream);

            inputStream.close();

            ftpClient.logout();

            flag = true;

            System.out.println("上传文件全程走完");

        } catch (Exception e) {

            System.out.println("上传文件失败");

            e.printStackTrace();

        } finally {

            if (ftpClient.isConnected()) {

                try {

                    ftpClient.disconnect();

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

            if (null != inputStream) {

                try {

                    inputStream.close();

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

        }

        return true;

    }
    public void initFtpClient() {

        ftpClient = new FTPClient();

        ftpClient.setControlEncoding("utf-8");

        try {

            System.out.println("connecting...ftp服务器:" + this.hostname + ":" + this.port);

            ftpClient.connect(hostname, port); // 连接ftp服务器

            ftpClient.login(username, password); // 登录ftp服务器

            int replyCode = ftpClient.getReplyCode(); // 是否成功登录服务器

            if (!FTPReply.isPositiveCompletion(replyCode)) {

                System.out.println("connect failed...ftp服务器:" + this.hostname + ":" + this.port);

            }

            System.out.println("connect successfu...ftp服务器:" + this.hostname + ":" + this.port);

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    /**

     * 上传文件

     *

     * @param pathname      ftp服务保存地址

     * @param fileName      上传到ftp的文件名

     * @param originfilename 待上传文件的名称（绝对地址） *

     * @return

     */

    public  boolean uploadFile1(String pathname, String fileName, String originfilename) {

        boolean flag = false;

        InputStream inputStream = null;

        try {

            System.out.println("开始上传文件");

            inputStream = new FileInputStream(new File(originfilename));

            initFtpClient();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            CreateDirecroty(pathname);

            ftpClient.makeDirectory(pathname);

            ftpClient.changeWorkingDirectory(pathname);



            ftpClient.storeFile(fileName, inputStream);

            inputStream.close();

            ftpClient.logout();

            flag = true;

            System.out.println("上传文件全程走完");

        } catch (Exception e) {

            System.out.println("上传文件失败");

            e.printStackTrace();

        } finally {

            if (ftpClient.isConnected()) {

                try {

                    ftpClient.disconnect();

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

            if (null != inputStream) {

                try {

                    inputStream.close();

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

        }

        return true;

    }

    /**

     * 上传文件

     *

     * @param pathname    ftp服务保存地址

     * @param fileName    上传到ftp的文件名

     * @param inputStream 输入文件流

     * @return

     */

    public boolean uploadFile(String pathname, String fileName, InputStream inputStream) {

        boolean flag = false;

        try {

            System.out.println("开始上传文件");

            initFtpClient();

            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);

            CreateDirecroty(pathname);

            ftpClient.makeDirectory(pathname);

            ftpClient.changeWorkingDirectory(pathname);

            ftpClient.storeFile(fileName, inputStream);

            inputStream.close();

            ftpClient.logout();

            flag = true;

            System.out.println("上传文件成功");

        } catch (Exception e) {

            System.out.println("上传文件失败");

            e.printStackTrace();

        } finally {

            if (ftpClient.isConnected()) {

                try {

                    ftpClient.disconnect();

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

            if (null != inputStream) {

                try {

                    inputStream.close();

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

        }

        return true;

    }

// 改变目录路径

    public boolean changeWorkingDirectory(String directory) {

        boolean flag = true;

        try {

            flag = ftpClient.changeWorkingDirectory(directory);

            if (flag) {

                System.out.println("进入文件夹" + directory + " 成功！");

            } else {

                System.out.println("进入文件夹" + directory + " 失败！开始创建文件夹");

            }

        } catch (IOException ioe) {

            ioe.printStackTrace();

        }

        return flag;

    }

// 创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建

    public boolean CreateDirecroty(String remote) throws IOException {

        boolean success = true;

        String directory = remote + "/";

// 如果远程目录不存在，则递归创建远程服务器目录

        if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(new String(directory))) {

            int start = 0;

            int end = 0;

            if (directory.startsWith("/")) {

                start = 1;

            } else {

                start = 0;

            }

            end = directory.indexOf("/", start);

            String path = "";

            String paths = "";

            while (true) {

                String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");

                path = path + "/" + subDirectory;

                if (!existFile(path)) {

                    if (makeDirectory(subDirectory)) {

                        changeWorkingDirectory(subDirectory);

                    } else {

                        System.out.println("创建目录[" + subDirectory + "]失败");

//System.out.println("上传文件失败");

                        changeWorkingDirectory(subDirectory);

                    }

                } else {

                    changeWorkingDirectory(subDirectory);

                }

                paths = paths + "/" + subDirectory;

                start = end + 1;

                end = directory.indexOf("/", start);

// 检查所有目录是否创建完毕

                if (end <= start) {

                    break;

                }

            }

        }

        return success;

    }

// 判断ftp服务器文件是否存在

    public boolean existFile(String path) throws IOException {

        boolean flag = false;

        FTPFile[] ftpFileArr = ftpClient.listFiles(path);

        if (ftpFileArr.length > 0) {

            flag = true;

        }

        return flag;

// 方法三:提示用户重名,需重新命名

    }

// 创建目录

    public boolean makeDirectory(String dir) {

        boolean flag = true;

        try {

            flag = ftpClient.makeDirectory(dir);

            if (flag) {

                System.out.println("创建文件夹" + dir + " 成功！");

            } else {

                System.out.println("创建文件夹" + dir + " 失败！");

//return false;

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return flag;

    }

    /**

     * * 下载文件 *

     *

     * @param pathname  FTP服务器文件目录 *

     * @param filename  文件名称 *

     * @param localpath 下载后的文件路径 *

     * @return

     */

    public boolean downloadFile(String pathname, String filename, String localpath) {

        boolean flag = false;

        OutputStream os = null;

        try {

            System.out.println("开始下载文件");

            initFtpClient();

// 切换FTP目录

            ftpClient.changeWorkingDirectory(pathname);

            FTPFile[] ftpFiles = ftpClient.listFiles();

            for (FTPFile file : ftpFiles) {

                if (filename.equalsIgnoreCase(file.getName())) {

                    File localFile = new File(localpath + "/" + file.getName());

                    os = new FileOutputStream(localFile);

                    ftpClient.retrieveFile(file.getName(), os);

                    os.close();

                }

            }

            ftpClient.logout();

            flag = true;

            System.out.println("下载文件成功");

        } catch (Exception e) {

            System.out.println("下载文件失败");

            e.printStackTrace();

        } finally {

            if (ftpClient.isConnected()) {

                try {

                    ftpClient.disconnect();

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

            if (null != os) {

                try {

                    os.close();

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

        }

        return flag;

    }

    /**

     * * 删除文件 *

     *

     * @param pathname FTP服务器保存目录 *

     * @param filename 要删除的文件名称 *

     * @return

     */

    public boolean deleteFile(String pathname, String filename) {

        boolean flag = false;

        try {

            System.out.println("开始删除文件");

            initFtpClient();

// 切换FTP目录

            ftpClient.changeWorkingDirectory(pathname);

            ftpClient.dele(filename);

            ftpClient.logout();

            flag = true;

            System.out.println("删除文件成功");

        } catch (Exception e) {

            System.out.println("删除文件失败");

            e.printStackTrace();

        } finally {

            if (ftpClient.isConnected()) {

                try {

                    ftpClient.disconnect();

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

        }

        return flag;

    }


}
