package com.gistone.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * excel 导出类
 * @author xjc
 *
 */
public class ExcelUtils {

	/**
	 * 数据导出
	 * @param list 查询数据
	 * @param name 标题名称
	 * @param order 列名
	 * @param orderName 列题名
	 * @param request
	 * @param response
	 * @throws Exception
	 *//*
    @SuppressWarnings({ "deprecation", "unused" })
	public static String exportExcelsToStream(List<Map<String,Object>> list, String name ,List<String> order,List<String> orderName,HttpServletRequest request,HttpServletResponse response, String path)throws Exception{
		//创建工作簿  
    	XSSFWorkbook workBook = new XSSFWorkbook();
    	//声明并初始化输出流
    	OutputStream output = null;
    	//创建字体对象--设置全局字体样式
		XSSFFont font = workBook.createFont();
		//设置字体名称
		font.setFontName("宋体");
		//设置字体粗细
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		//设置字体高度
		font.setFontHeightInPoints((short) 11);
		
		//创建单元格样式对象--设置全局单元格样式
		XSSFCellStyle style = workBook.createCellStyle();
		//设置字体格式
		style.setFont(font);
		//水平居中
		style.setAlignment(CellStyle.ALIGN_CENTER);
		//垂直居中
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//下边框
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		//右边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
    	
		//创建sheet页对象（excel的表单）
		XSSFSheet sheet = workBook.createSheet(name);
		style.setWrapText(true);//设置自动换行  
    	
		// 定义列
		XSSFCell cell = null;
		//创建表头并获取数据开始行
		int index = generateHeader(sheet, style, order, orderName);
		//声明开始行
		int i = 0;
		for (Map<String,Object> data : list) {
			i++;
			//创建一行
			XSSFRow row1 = sheet.createRow(i);
			for(int j =0  ; j < order.size() ; j++){
				//获取列
				cell = row1.createCell(j);  
				//遍历行数据
				for (Object k : data.keySet())
				{
					if(k.equals(order.get(j)) || k == order.get(j)){
						//给列赋值
						cell.setCellValue(data.get(k).toString());  
					}
				}
			}
			
		}
		//创建本地文件
		*//*
		File file =new File("C://" +name+".xls");
			if(!file.exists()){	//判断该文件是否存在
				file.createNewFile();		//如果不存在该文件，则使用file.createNewFile()创建该文件
			}
			workBook.write(new File(file.toString()));
			workBook.close();//最后记得关闭工作簿  
		*//*
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
		File dir = new File(path+"/"+df.format(new Date())+"/");
		if (!dir.exists() && !dir.isDirectory())
			dir.mkdirs();
		String filePath = path+"/"+df.format(new Date())+"/"+ name + ".xlsx";
		// 输出Excel文件
		try {
			output = new FileOutputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		response.setContentType("application/msexcel");
		workBook.write(output);	
		workBook.close();//最后记得关闭工作簿 
		
		output.flush();
		output.close();
		
		return filePath.substring(filePath.indexOf("/attached"), filePath.length());
	}
    
    *//**
     * 处理表头
     * @param sheet
     * @param style
     * @return
     *//*
	private static int generateHeader(XSSFSheet sheet, XSSFCellStyle style, List<String> order,List<String> orderName) {
    	
    	XSSFRow row = null;  
		XSSFCell cell = null;
		//设置列宽
		sheet.setDefaultColumnWidth(20);
		sheet.setColumnWidth(0, 2500);
		//第一行
		row = sheet.createRow(0);
		//遍历插入标题
		for(int k = 0 ; k < order.size() ; k ++){
			cell = row.createCell(k);
			cell.setCellValue(orderName.get(k)); 
		}
    	
    	return 3;
    }*/
    
}
