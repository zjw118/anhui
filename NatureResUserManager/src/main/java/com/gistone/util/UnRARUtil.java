package com.gistone.util;

import java.io.File;
import java.io.FileOutputStream;

import de.innosystec.unrar.Archive;
import de.innosystec.unrar.rarfile.FileHeader;

public class UnRARUtil {

	/**  
	    * 解压rar格式压缩包。  
	    * 对应的是java-unrar-0.3.jar，但是java-unrar-0.3.jar又会用到commons-logging-1.1.1.jar  
	    */   
	   public static void unrar(String sourceRar,String destDir) throws Exception{    
	       Archive a = null;
	       FileOutputStream fos = null;    
	       try{    
	           a = new Archive(new File(sourceRar));
	           FileHeader fh = a.nextFileHeader();    
	           while(fh!=null){    
	               if(!fh.isDirectory()){    
	                   //1 根据不同的操作系统拿到相应的 destDirName 和 destFileName
	            	   String compressFileName="";
	            	   if (fh.isUnicode()) {	//解决文件中有中文，出现乱码的问题
	   					compressFileName = fh.getFileNameW().trim();	//处理有中文
	   					} else {
	   					compressFileName = fh.getFileNameString().trim();	//处理没有中文
	   					}
//	                   String compressFileName = fh.getFileNameString().trim();    
	                   String destFileName = "";    
	                   String destDirName = "";    
	                   //非windows系统    
	                   if(File.separator.equals("/")){    
	                       destFileName = destDir.replaceAll("/", "\\\\") + compressFileName.replaceAll("\\\\", "/");    
	                       destDirName = destFileName.substring(0, destFileName.lastIndexOf("/"));    
	                   //windows系统     
	                   }else{    
	                       destFileName = destDir.replaceAll("/", "\\\\") + compressFileName.replaceAll("/", "\\\\");    
	                       destDirName = destFileName.substring(0, destFileName.lastIndexOf("\\"));    
	                   }    
	                   //2创建文件夹    
	                   File dir = new File(destDirName);    
	                   if(!dir.exists()||!dir.isDirectory()){    
	                       dir.mkdirs();    
	                   }    
	                   //3解压缩文件    
	                   fos = new FileOutputStream(new File(destFileName)); 
	                   System.out.println(destFileName);
	                   try {
	                	   a.extractFile(fh, fos);    
	                   } catch (Exception e) {
	                	   e.printStackTrace();
	                   }
	                   
	                   fos.close();    
	                   fos = null;    
	               }    
	               fh = a.nextFileHeader();    
	           }    
	           a.close();    
	           a = null;    
	       }catch(Exception e){    
	           throw e;    
	       }finally{    
	           if(fos!=null){    
	               try{fos.close();fos=null;}catch(Exception e){e.printStackTrace();}    
	           }    
	           if(a!=null){    
	               try{a.close();a=null;}catch(Exception e){e.printStackTrace();}    
	           }    
	       }    
	   }

	  
	//解压arcgis文件
	//compressedUrl：压缩文件
	//arcgisUrl：目标文件夹
	//uUIDFileName:唯一文件名
	//功能描述：将压缩文件解压缩到目标文件夹，返回需要的文件名称（.shp或.tif）
	public static String unRarArcGISFiles(String compressedUrl, String arcgisUrl,String uuidStr) throws Exception {
		String fileUrl = null;
		Archive a = null;
	    FileOutputStream fos = null; 
	    //带路径文件名
	    String compressFileName="";
	    try{    
	    	//1.创建压缩文件对象
	        a = new Archive(new File(compressedUrl));
	        FileHeader fh = a.nextFileHeader();    
	        while(fh!=null){
	        	if(!fh.isDirectory()){    
	        		//1 根据不同的操作系统拿到相应的 destDirName 和 destFileName
	        		if (fh.isUnicode()) {	//解决文件中有中文，出现乱码的问题
	        			compressFileName = fh.getFileNameW().trim();//处理有中文
	        		}else{
	        			compressFileName = fh.getFileNameString().trim();//处理没有中文
	        		}
	        		
	        		//目标文件绝对路径
	        		String destFileName = "";
	        		//目标文件夹绝对路径
	        		String destDirName = "";    
					//非windows系统    
					if(File.separator.equals("/")){
						compressFileName = compressFileName.replaceAll("\\\\", "/");
						//获取文件名，去掉文件夹路径
						if(compressFileName.lastIndexOf("/") != -1){
							 compressFileName = compressFileName.substring(compressFileName.lastIndexOf("/")+1);
						}
						   
						//获取目标文件路径
					    destFileName = arcgisUrl + "/"+uuidStr+"_"+ compressFileName;
					    destFileName = destFileName.replaceAll("\\\\", "/");
					    destDirName = destFileName.substring(0, destFileName.lastIndexOf("/"));    
					//windows系统     
					}else{
						compressFileName.replaceAll("/", "\\\\");
						//获取文件名，去掉文件夹路径
						if(compressFileName.lastIndexOf("\\") != -1){
							 compressFileName = compressFileName.substring(compressFileName.lastIndexOf("\\")+1);
						}
						   
						//获取目标文件路径
						destFileName = arcgisUrl + "\\"+uuidStr+"_"+ compressFileName;
					    destFileName = destFileName.replaceAll("/", "\\\\");
					    destDirName = destFileName.substring(0, destFileName.lastIndexOf("\\"));    
					}
					
					String shapeName = destFileName.substring(destFileName.lastIndexOf("."));
					if(shapeName.equals(".tif") || shapeName.equals(".img")){
						   fileUrl = uuidStr +"_"+ compressFileName;
					}
					
					//2创建文件夹    
					File dir = new File(destDirName);    
					if(!dir.exists()||!dir.isDirectory()){    
					    dir.mkdirs();
					}    
					
					//3解压缩文件    
					fos = new FileOutputStream(new File(destFileName)); 
					System.out.println(destFileName);
					try {
						a.extractFile(fh, fos);    
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					fos.close();
					fos = null;
	        	}
	        	fh = a.nextFileHeader();
	        }
	        a.close();
			a = null;
	    }catch(Exception e){    
	    	throw e;    
	    }finally{    
	    	if(fos!=null){    
	    		try{fos.close();fos=null;}catch(Exception e){e.printStackTrace();}    
	    	}    
	    	if(a!=null){    
	    		try{a.close();a=null;}catch(Exception e){e.printStackTrace();}    
	    	}    
	    }
	    
	    return fileUrl;    
	}    
}
