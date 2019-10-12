package com.gistone.bjhky.util;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class EditPhotoUtil {
	
	/** 
     * 采用指定宽度、高度或压缩比例 的方式对图片进行压缩 
     * @param imgsrc 源图片地址 
     * @param imgdist 目标图片地址 
     * @param widthdist 压缩后图片宽度（当rate==null时，必传） 
     * @param heightdist 压缩后图片高度（当rate==null时，必传） 
     * @param rate 压缩比例  
     */  
	public static void reduceImg(String imgsrc, String imgdist, int widthdist, int heightdist, Float rate) {  
        try {  
            File srcfile = new File(imgsrc);  
            // 检查文件是否存在  
            if (!srcfile.exists()) {  
                return;  
            }  
            // 如果rate不为空说明是按比例压缩  
            if (rate != null && rate > 0) {  
                // 获取文件高度和宽度  
                int[] results = getImgWidth(srcfile);  
                if (results == null || results[0] == 0 || results[1] == 0) {  
                    return;  
                } else {  
                    widthdist = (int) (results[0] * rate);  
                    heightdist = (int) (results[1] * rate);  
                }  
            }  
            // 开始读取文件并进行压缩  
            Image src = ImageIO.read(srcfile);
            BufferedImage tag = new BufferedImage((int) widthdist,  
                    (int) heightdist, BufferedImage.TYPE_INT_RGB);  
  
            tag.getGraphics().drawImage(  
                    src.getScaledInstance(widthdist, heightdist,  
                            Image.SCALE_SMOOTH), 0, 0, null);  
  
            FileOutputStream out = new FileOutputStream(imgdist);  
            String formatName = imgdist.substring(imgdist.lastIndexOf(".")+1);
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
//            encoder.encode(tag);  
            ImageIO.write(tag, formatName, new File(imgdist));
            out.close();
  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
    }
	
	/** 
     * 获取图片宽高 
     *  
     * @param file
     * 
     * @return 宽高 
     */  
	public static int[] getImgWidth(File file) {  
        InputStream is = null;  
        BufferedImage src = null;  
        int result[] = { 0, 0 };  
        try {  
            is = new FileInputStream(file);  
            src = ImageIO.read(is);
            result[0] = src.getWidth(null); // 得到源图宽  
            result[1] = src.getHeight(null); // 得到源图高  
            is.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return result;  
    }
	
	/**
	 * 压缩图片文件大小，图片尺寸不变
	 * @param imgsrc 源图片地址
	 * @param imgdist 目标图片地址
	 */
	public static void imageCompression(String imgsrc, String imgdist){
		try {
			//源图片文件
			File fromPic=new File(imgsrc);  
			//处理后的图片文件
			File toPic=new File(imgdist);
			String formatName = imgdist.substring(imgdist.lastIndexOf(".")+1);
			BufferedImage bi=Thumbnails.of(fromPic).scale(0.5f).outputQuality(0.25f).asBufferedImage();  
			ImageIO.write(bi,formatName,toPic);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
