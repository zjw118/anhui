package com.gistone.util;

import org.apache.tools.ant.util.DateUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;

/**
 * 上传图片
 * @author xjc
 *
 */
public class PictureUtilsCh {
	
	/**
	 *
	 * 得到上传图片路径
	 * @param path 服务器本地存储的路径+当天的时间+0 比如"epr\temp\importTask\20191113\0"
	 * @param file 前端上传的文件file
	 * @return
	 */
	public static String getPicturePath(String path, MultipartFile file) {
		
		String filePath = null;
		
		if(file == null) {
			filePath = null;
		}else {
		
			String format1 = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),file.getOriginalFilename().length());
			String excelName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."))+format1;
			String nowTime = DateUtils.format(new Date(), "yyyyMMdd");
			
			if (!file.isEmpty()) {
				
				InputStream in = null;
				OutputStream out = null;
				OutputStream out2  = null;
				
				try {
					int num = 0;   //文件夹，0 Excel，1 文档，2 PDF
					String format = excelName.substring(excelName.lastIndexOf(".")+1);
					if(format.equals("xls") || format.equals("xlsx")){
						num = 0;
					}else if (format.equals("docx")) {
						num = 1;
					}else if (format.equals("pdf")) {
						num = 2;
					}else if(format.equals("txt")) {
						num = 3;
					}else if(format.equals("jpg") || format.equals("png")
                            || format.equals("jpeg") || format.equals("gif")) {
						num = 4;
					}else if(format.equals("amr")) {
						num = 5;
					}else{
					    num=7;
                    }
					
					File dir = new File(path);
					if (!dir.exists())
						dir.mkdirs();
					filePath = path + nowTime +"/"+num+"/"+excelName;
					
					File serverFile  = new File(filePath);
					in = file.getInputStream();
					//判断文件夹是否存在，不存在则创建
					File excelPathFile =new File(path + File.separator + nowTime +"/"+num+"/");  
					if(!excelPathFile.exists() && !excelPathFile.isDirectory()){
						excelPathFile.mkdirs();
					} 
					out  = new FileOutputStream(serverFile);
					byte[] b = new byte[1024];
					int len = 0;
					while ((len = in.read(b)) > 0) {
						out .write(b, 0, len);
					}
					System.out.println("图片生成成功!!!!!!!!!!!!!!!!!!!!!!!!!");
				} catch (IOException e) {
					e.printStackTrace();
				}finally {
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

	/**
	 * 得到前端上传的文件在服务器上存储的路径
	 * @param path 后台定义的在服务器上存储的路径
	 * @param file 前端上传的文件
	 * @return
	 */
	public static String getDiyTempFilePath(String path, MultipartFile file) {

		String filePath = null;

		if(file == null) {
			filePath = null;
		}else {

			String format1 = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),file.getOriginalFilename().length());
			String excelName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."))+format1;
			String nowTime = DateUtils.format(new Date(), "yyyyMMdd");

			if (!file.isEmpty()) {

				InputStream in = null;
				OutputStream out = null;
				OutputStream out2  = null;

				try {
//					int num = 0;   //文件夹，0 Excel，1 文档，2 PDF
//					String format = excelName.substring(excelName.lastIndexOf(".")+1);
//					if(format.equals("xls") || format.equals("xlsx")){
//						num = 0;
//					}else if (format.equals("docx")) {
//						num = 1;
//					}else if (format.equals("pdf")) {
//						num = 2;
//					}else if(format.equals("txt")) {
//						num = 3;
//					}else if(format.equals("jpg") || format.equals("png")
//							|| format.equals("jpeg") || format.equals("gif")) {
//						num = 4;
//					}else if(format.equals("amr")) {
//						num = 5;
//					}else{
//						num=7;
//					}

					File dir = new File(path);
					if (!dir.exists())
						dir.mkdirs();
					filePath = path +"/"+excelName;

					File serverFile  = new File(filePath);
					in = file.getInputStream();
					//判断文件夹是否存在，不存在则创建
					File excelPathFile =new File(path + File.separator +"/");
					if(!excelPathFile.exists() && !excelPathFile.isDirectory()){
						excelPathFile.mkdirs();
					}
					out  = new FileOutputStream(serverFile);
					byte[] b = new byte[1024];
					int len = 0;
					while ((len = in.read(b)) > 0) {
						out .write(b, 0, len);
					}
					System.out.println("图片生成成功!!!!!!!!!!!!!!!!!!!!!!!!!");
				} catch (IOException e) {
					e.printStackTrace();
				}finally {
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
}
