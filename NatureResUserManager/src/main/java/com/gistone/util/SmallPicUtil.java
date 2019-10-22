package com.gistone.util;

public class SmallPicUtil {

	/*** <p>Title:FfmpegDemo </p>* <p>Description: </p>*  * @author xuwei* @date下午2:49:59 
	 * vname：视频及图片名称
	 * dirStr：存储路径
	 * ffmpegPath：bat文件
	 * @return */
	public static String getSmallPic(String vname,String dirStr,String ffmpegPath,String projectPath){
		//String dirStr=System.getProperty("user.dir");
		//视频文件  
		//String videoRealPath =dirStr+ "\\"+vname+".mp4";  
		String videoRealPath =dirStr+ "\\"+vname;  

		 //截图的路径（输出路径）  
	    String imageRealPath =dirStr+"\\"+vname.substring(0,vname.indexOf('.'))+".jpg";  
	    try {  
	        //调用批处理文件  
	    	//String string = "cmd /c start "+ffmpegPath+" " + videoRealPath + " " + imageRealPath;
	    	
	        //Runtime.getRuntime().exec("cmd /c start "+ffmpegPath+" " + videoRealPath + " " + imageRealPath);
	    	String cmd = projectPath + "/ffmpeg/ffmpeg.exe -i "+videoRealPath+" -ss 2 -vframes 1 -r 1 -ac 1 -ab 2 -s 160*120 -f  image2 "+imageRealPath +" &&　exit";
	    	System.out.println(cmd);
	    	Runtime.getRuntime().exec("cmd /c start " +cmd);
	    	return vname.substring(0,vname.indexOf('.'))+".jpg";
	    } catch (Exception e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	        return null;
	    }
	}
}
