package com.gistone.bjhky.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcUtil {
	    private static POIFSFileSystem fs;  
	    private static HSSFWorkbook wb;  
	    private static HSSFSheet sheet;  
	    private static HSSFRow row;
	public static String getStandardDate(String date){
		if(date.contains("-")){
			date = date.replace("-", "/");
		}
		int length = date.length();
		String str = date;
		if(length==10){
			return date;
		}else{
			String aa = date.substring(date.indexOf("/")+1,date.lastIndexOf("/"));
			if(aa.length()==1){
				aa=date.substring(0,4)+"/0"+aa;
			}else{
				aa=str.substring(0,str.lastIndexOf("/"));
			}
			String bb = date.substring(date.lastIndexOf("/")+1);
			if(bb.length()==1){

				bb=aa+"/0"+bb;
				return bb;
			}else{
				aa+="/"+bb;
				return aa;
			}

		}
	}
	/**
	 * 利用正则表达式判断字符串是否是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){

		try {
			double db=Double.parseDouble(String.valueOf(str));

		} catch (Exception e) {
			return false;
		}
		return true;
	}
	    /** 
	     * 读取Excel表格表头的内容 
	     * @param is
	     * @return String 表头内容的数组 
	     */  
	    public String[] readExcelTitle(InputStream is) {  
	        try {  
	            fs = new POIFSFileSystem(is);  
	            wb = new HSSFWorkbook(fs);  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        sheet = wb.getSheetAt(0);  
	        row = sheet.getRow(0);  
	        // 标题总列数  
	        int colNum = row.getPhysicalNumberOfCells();  
	        System.out.println("colNum:" + colNum);  
	        String[] title = new String[colNum];  
	        for (int i = 0; i < colNum; i++) {  
	            //title[i] = getStringCellValue(row.getCell((short) i));  
	            title[i] = getCellFormatValue(row.getCell((short) i));  
	        }  
	        return title;  
	    }  
	  
	    /** 
	     * 读取Excel数据内容 
	     * @param is
	     * @return Map 包含单元格数据内容的Map对象 
	     */  
	    public static List<Map> readExcelContent(InputStream is) {  
	        Map<Integer, String> content = new HashMap<Integer, String>();  
	        String str = "";  
	        try {  
	            fs = new POIFSFileSystem(is);  
	            wb = new HSSFWorkbook(fs);  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        sheet = wb.getSheetAt(0);  
	        // 得到总行数  
	        int rowNum = sheet.getLastRowNum();  
	        row = sheet.getRow(0);  
	        int colNum = row.getPhysicalNumberOfCells();  
	        List<Map> list=new ArrayList<Map>();
	        // 正文内容应该从第二行开始,第一行为表头的标题  
	        for (int i = 1; i <= rowNum; i++) {  
	        	Map map=new HashMap<String, Object>();
	            row = sheet.getRow(i);  
	            int j = 0;  
	            while (j < colNum) {  
	                // 每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据  
	                // 也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean  
	                // str += getStringCellValue(row.getCell((short) j)).trim() +  
	                // "-";
					Object aa=sheet.getRow(0).getCell(j);
					if(aa==null){

					}else if(sheet.getRow(0).getCell(j).toString().indexOf("（") == -1){
	            		map.put(getStringCellValue(sheet.getRow(0).getCell(j)), removePoint(getCellFormatValue(row.getCell((short) j)).trim().toString())); 
	            	}else{
	            		map.put(getStringCellValue(sheet.getRow(0).getCell(j)).substring(0, sheet.getRow(0).getCell(j).toString().indexOf("（")), removePoint(getCellFormatValue(row.getCell((short) j)).trim().toString())); 
	            	}
	                j++;  
	            }  
	            map.put("row", i);
	            list.add(map);
	        }  
	        return list;  
	    }  
	    
	    /** 
	     * 读取Excel数据全部内容 
	     * @param is
	     * @return Map 包含单元格数据内容的Map对象 
	     */  
	    public static List<Map<String,Object>> readExcelContentAll(InputStream is) {  
	        Map<Integer, String> content = new HashMap<Integer, String>();  
	        String str = "";  
	        try {  
	            fs = new POIFSFileSystem(is);  
	            wb = new HSSFWorkbook(fs);  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        sheet = wb.getSheetAt(0);  
	        // 得到总行数  
	        int rowNum = sheet.getLastRowNum();  
	        row = sheet.getRow(4);  
	        int colNum = row.getPhysicalNumberOfCells();  
	        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
	        // 正文内容应该从第二行开始,第一行为表头的标题  
	        for (int i = 0; i <= rowNum; i++) {  
	        	Map<String,Object> map=new HashMap<String, Object>();
	            row = sheet.getRow(i);  
	            int j = 0;  
	            while (j < colNum) {  
	                // 每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据  
	                // 也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean  
	                // str += getStringCellValue(row.getCell((short) j)).trim() +  
	                // "-";
	            	if(sheet.getRow(i).getCell(j)!=null){
		            	map.put(j+"", removePoint(getCellFormatValue(row.getCell((short) j)).trim().toString()));
	            	}else{
	            		map.put(j+"", null);
	            	}
	                j++;  
	            }  
	            map.put("row", i);
	            list.add(map);
	        }  
	        return list;  
	    }
	  
	    /** 
	     * 获取单元格数据内容为字符串类型的数据 
	     *  
	     * @param cell Excel单元格 
	     * @return String 单元格数据内容 
	     */  
	    private static String getStringCellValue(HSSFCell cell) {  
	        String strCell = "";  
	        switch (cell.getCellType()) {  
	        case HSSFCell.CELL_TYPE_STRING:  
	            strCell = cell.getStringCellValue();
	            break;
	        case HSSFCell.CELL_TYPE_NUMERIC:  
	            strCell = String.valueOf(cell.getNumericCellValue());  
	            break;  
	        case HSSFCell.CELL_TYPE_BOOLEAN:  
	            strCell = String.valueOf(cell.getBooleanCellValue());  
	            break;  
	        case HSSFCell.CELL_TYPE_BLANK:  
	            strCell = "";  
	            break;  
	        default:  
	            strCell = "";  
	            break;  
	        }  
	        if (strCell.equals("") || strCell == null) {  
	            return "";  
	        }  
	        if (cell == null) {  
	            return "";  
	        }  
	        return strCell;  
	    }  
	  
	    /** 
	     * 获取单元格数据内容为日期类型的数据 
	     *  
	     * @param cell 
	     *            Excel单元格 
	     * @return String 单元格数据内容 
	     */  
	    private String getDateCellValue(HSSFCell cell) {  
	        String result = "";
	        try {  
	            int cellType = cell.getCellType();  
	            if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {  
	                Date date = cell.getDateCellValue();  
	                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)  
	                        + "-" + date.getDate();  
	            } else if (cellType == HSSFCell.CELL_TYPE_STRING) {  
	                String date = getStringCellValue(cell);  
	                result = date.replaceAll("[年月]", "-").replace("日", "").trim();  
	            } else if (cellType == HSSFCell.CELL_TYPE_BLANK) {  
	                result = "";  
	            }  
	        } catch (Exception e) {  
	            System.out.println("日期格式不正确!");  
	            e.printStackTrace();  
	        }  
	        return result;  
	    }  
	  
	    /** 
	     * 根据HSSFCell类型设置数据 
	     * @param cell 
	     * @return 
	     */  
	    private static String getCellFormatValue(HSSFCell cell) {  
	        String cellvalue = "";  
	        if (cell != null) {  
	            // 判断当前Cell的Type  
	            switch (cell.getCellType()) {  
	            // 如果当前Cell的Type为NUMERIC  
	            case HSSFCell.CELL_TYPE_NUMERIC: {
	            	cell.setCellType(Cell.CELL_TYPE_STRING);
	            	cellvalue = cell.getStringCellValue();
	            	break;
	            }  
	            case HSSFCell.CELL_TYPE_FORMULA: {  
	                // 判断当前的cell是否为Date  
	                if (HSSFDateUtil.isCellDateFormatted(cell)) {  
	                    // 如果是Date类型则，转化为Data格式  
	                      
	                    //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00  
	                    //cellvalue = cell.getDateCellValue().toLocaleString();  
	                      
	                    //方法2：这样子的data格式是不带带时分秒的：2011-10-12  
	                    Date date = cell.getDateCellValue();  
	                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	                    cellvalue = sdf.format(date);  
	                      
	                }  
	                // 如果是纯数字  
	                else {  
	                    // 取得当前Cell的数值  
	                    cellvalue = String.valueOf(cell.getNumericCellValue());  
	                }  
	                break;  
	            }  
	            // 如果当前Cell的Type为STRIN  
	            case HSSFCell.CELL_TYPE_STRING:  
	                // 取得当前的Cell字符串  
	                cellvalue = cell.getRichStringCellValue().getString();  
	                break;  
	            // 默认的Cell值  
	            default:  
	                cellvalue = "";  
	            }  
	        } else {  
	            cellvalue = "";  
	        }  
	        return cellvalue;  
	  
	    }  
	    public static String removePoint(String data){
	    	if(data.indexOf(".") == -1){
	    		return data;
	    	}else{
	    		if(data.contains("°")&&data.contains("′")&&data.contains(".")&&data.contains("″")){
	    			return data;
				}else{
	    			return data;
				}
	    	}
	    }


		/*public static void exportExcel(List<Map> list, String name ,List<String> order,List<String> orderName) {
			  //创建工作簿  
	        HSSFWorkbook workBook = new HSSFWorkbook();  
	        //创建工作表
	        HSSFSheet sheet = workBook.createSheet("数据");
	        HSSFRow row = sheet.createRow(0);
	        HSSFCell cell = null;
	        //遍历插入标题
	        for(int k = 0 ; k < order.size() ; k ++){
	        	cell = row.createCell(k);
	        	 cell.setCellValue(orderName.get(k)); 
	        }
	        //声明开始行
	        int i = 0;
	        for (Map<String,Object> data : list) {
				i++;
				//创建一行
				row = sheet.createRow(i);
				for(int j =0  ; j < order.size() ; j++){
					//获取列
					cell = row.createCell(j);  
					//遍历行数据
					 for (Object k : data.keySet())
				      {
						 if(k.equals(order.get(j)) || k == order.get(j)){
							 //给列赋值
							 String k23 = (String)k;
							 if(k23.equals("ssd_weather")){
								 int jk = 4;
								 System.err.println(jk);
							 }
							 cell.setCellValue(data.get(k).toString());  
						 }
				      }
				}
				
			}
	        //创建本地文件
	        File file =new File("C://" +name+".xls");
	        try {
	        	if(!file.exists()){	//判断该文件是否存在
     				file.createNewFile();		//如果不存在该文件，则使用file.createNewFile()创建该文件
     			}
				workBook.write(new File(file.toString()));
				workBook.close();//最后记得关闭工作簿 
				
				//返回前台
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		*/
		
		/*public static void exportExcelFile(List<Map> list, String name ,List<String> order,List<String> orderName,HttpServletRequest request,HttpServletResponse response) throws Exception{
			//创建工作簿  
	        HSSFWorkbook workBook = new HSSFWorkbook();  
	        //创建工作表
	        HSSFSheet sheet = workBook.createSheet("数据");
	        HSSFRow row = sheet.createRow(0);
	        HSSFCell cell = null;
	        //遍历插入标题
	        for(int k = 0 ; k < order.size() ; k ++){
	        	cell = row.createCell(k);
	        	 cell.setCellValue(orderName.get(k)); 
	        }
	        //声明开始行
	        int i = 0;
	        for (Map<String,Object> data : list) {
				i++;
				//创建一行
				row = sheet.createRow(i);
				for(int j =0  ; j < order.size() ; j++){
					//获取列
					cell = row.createCell(j);  
					//遍历行数据
					 for (Object k : data.keySet())
				      {
						 if(k.equals(order.get(j)) || k == order.get(j)){
							 //给列赋值
							 String k23 = (String)k;
							 if(k23.equals("ssd_weather")){
								 int jk = 4;
								 System.err.println(jk);
							 }
							 cell.setCellValue(data.get(k).toString());  
						 }
				      }
				}
				
			}
	        //创建本地文件
        	
        	name =name+".xls";
			//第一步：设置响应类型
			response.setContentType(request.getServletContext().getMimeType(name));//应用程序强制下载
		    //设置响应头，对文件进行url编码
		    name = URLEncoder.encode(name, "UTF-8");
		    response.setHeader("Content-Disposition", "attachment;filename="+name);   
		    
		    //第三步：老套路，开始copy
		    OutputStream out = response.getOutputStream();
		    
			workBook.write(out);
			workBook.close();//最后记得关闭工作簿 
			
			out.flush();
		    out.close();
			
			//返文件对象
				
		}*/
		
		/*//导出统计图数据
		public static void exportEChartExcel(HttpServletRequest request , HttpServletResponse response , String [] xlist,String [] yList, String name ,String type) throws IOException {
			  //创建工作簿  
	        HSSFWorkbook workBook = new HSSFWorkbook();  
	        //创建工作表
	        HSSFSheet sheet = workBook.createSheet(type);
	        HSSFRow row = sheet.createRow(0);
	        HSSFCell cell = null;
	        //遍历插入标题
	        for(int k = 0 ; k < xlist.length ; k ++){
	        	cell = row.createCell(k+1);
	        	 cell.setCellValue(xlist[k]); 
	        }
	        int i = 0 ;
	        for(int j = 0 ; j < yList.length ; j++){
	        	i++;
	        	row = sheet.createRow(i);
	        	for(int k = 0 ; k < yList[j].split(",").length ; k++){
	        		String y[] = yList[j].split(",");
	        		cell = row.createCell(k);  
	        		cell.setCellValue(y[k]);
	        	
	        	}
	        }
	        name =name+".xls";
	        //第一步：设置响应类型
			response.setContentType(request.getServletContext().getMimeType(name));//应用程序强制下载
		    //设置响应头，对文件进行url编码
		    try {
				name = URLEncoder.encode(name, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    response.setHeader("Content-Disposition", "attachment;filename="+name);   
		    
		    //第三步：老套路，开始copy
		    OutputStream out = response.getOutputStream();
		    
			workBook.write(out);
			workBook.close();//最后记得关闭工作簿 
			
			out.flush();
		    out.close();
			
		}*/
		//导出样线监测数据统计图数据,数据存储在session中
				/*public static void exportEChartSimplineExcel(HttpServletRequest request , HttpServletResponse response ) throws IOException {
					
					HttpSession session = request.getSession();
//					String[] xlist = (String[]) session.getAttribute("x");
//					String[] yList = (String[]) session.getAttribute("y");
					JSONArray ylist = (JSONArray) session.getAttribute("y");
					JSONArray xlist = (JSONArray) session.getAttribute("x");
//					List<JSONArray> ylist = new ArrayList();
//					ylist.add(y);
					String name =  session.getAttribute("name").toString();
					String type =  session.getAttribute("type").toString();
					//创建工作簿  
			        HSSFWorkbook workBook = new HSSFWorkbook();  
			        //创建工作表
			        HSSFSheet sheet = workBook.createSheet(type);
			        HSSFRow row = sheet.createRow(0);
			        HSSFCell cell = null;
			        //遍历插入标题
			        for(int k = 0 ; k < xlist.size() ; k ++){
			        	cell = row.createCell(k+1);
			        	 cell.setCellValue(xlist.get(k).toString()); 
			        }
			        int i = 0 ;
			        for(int j = 0 ; j < ylist.size() ; j++){
			        	i++;
			        	row = sheet.createRow(i);
			        		System.err.println(ylist.get(j));
			        	for(int k = 0 ; k < ylist.get(j).toString().split(",").length ; k++){
			        		cell = row.createCell(k);  
			        		cell.setCellValue(ylist.get(j).toString().substring(1, ylist.get(j).toString().length()-1).split(",")[k]);
			        	
			        	}
			        }
			        name =name+".xls";
			        //第一步：设置响应类型
					response.setContentType(request.getServletContext().getMimeType(name));//应用程序强制下载
				    //设置响应头，对文件进行url编码
				    try {
						name = URLEncoder.encode(name, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    response.setHeader("Content-Disposition", "attachment;filename="+name);   
				    
				    //第三步：老套路，开始copy
				    OutputStream out = response.getOutputStream();
				    
					workBook.write(out);
					workBook.close();//最后记得关闭工作簿 
					
					out.flush();
				    out.close();
					
				}*/
		/*public static void exportExcels(List<Map> list, String name ,List<String> order,List<String> orderName) {
			  //创建工作簿  
	        HSSFWorkbook workBook = new HSSFWorkbook();  
	        //创建工作表
	        HSSFSheet sheet = workBook.createSheet("数据");
	        HSSFRow row = sheet.createRow(0);
	        HSSFCell cell = null;
	        //遍历插入标题
	        for(int k = 0 ; k < order.size() ; k ++){
	        	cell = row.createCell(k);
	        	 cell.setCellValue(orderName.get(k)); 
	        }
	        //声明开始行
	        int i = 0;
	        for (Map data : list) {
				i++;
				//创建一行
				row = sheet.createRow(i);
				for(int j =0  ; j < order.size() ; j++){
					//获取列
					cell = row.createCell(j);  
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
	        File file =new File("C://" +name+".xls");
	        try {
	        	if(!file.exists()){	//判断该文件是否存在
 				file.createNewFile();		//如果不存在该文件，则使用file.createNewFile()创建该文件
 			}
				workBook.write(new File(file.toString()));
				workBook.close();//最后记得关闭工作簿  
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
		/**
		 * 文件输出到流
		 * @author luowenbin
		 * @date 2018年3月29日
		 */
		/*public static void exportExcelsToStream(List<Map> list, String name ,List<String> order,List<String> orderName,HttpServletRequest request,HttpServletResponse response)throws Exception{
			//创建工作簿  
			HSSFWorkbook workBook = new HSSFWorkbook();  
			//创建工作表
			HSSFSheet sheet = workBook.createSheet("数据");
			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = null;
			//遍历插入标题
			for(int k = 0 ; k < order.size() ; k ++){
				cell = row.createCell(k);
				cell.setCellValue(orderName.get(k)); 
			}
			//声明开始行
			int i = 0;
			for (Map data : list) {
				i++;
				//创建一行
				row = sheet.createRow(i);
				for(int j =0  ; j < order.size() ; j++){
					//获取列
					cell = row.createCell(j);  
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
			
			name =name+".xls";
			//第一步：设置响应类型
			response.setContentType(request.getServletContext().getMimeType(name));//应用程序强制下载
		    //设置响应头，对文件进行url编码
		    name = URLEncoder.encode(name, "UTF-8");
		    response.setHeader("Content-Disposition", "attachment;filename="+name);   
		    
		    //第三步：老套路，开始copy
		    OutputStream out = response.getOutputStream();
		    
			workBook.write(out);
			workBook.close();//最后记得关闭工作簿 
			
			out.flush();
		    out.close();
			
		}*/
}
