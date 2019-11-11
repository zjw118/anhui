package com.gistone.util;

import java.io.*;

public class DataBaseBackUp {

	/** 
	 * MySQL数据库备份
	 *  
	 * @author  
	 */  
	    public static boolean exportDatabase(String hostIP,String port, String userName, String password,
											 String savePath, String fileName, String databaseName) throws InterruptedException {
	        /** 
		     * Java代码实现MySQL数据库导出 
		     *  
		     * @author GaoHuanjie 
		     * @param hostIP MySQL数据库所在服务器地址IP 
		     * @param userName 进入数据库所需要的用户名 
		     * @param password 进入数据库所需要的密码 
		     * @param savePath 数据库导出文件保存路径 
		     * @param fileName 数据库导出文件文件名 
		     * @param databaseName 要导出的数据库名 
		     * @return 返回true表示导出成功，否则返回false。 
		     */  
	    	File saveFile = new File(savePath);  
	        if (!saveFile.exists()) {// 如果目录不存在  
	            saveFile.mkdirs();// 创建文件夹  
	        }  
	        if(!savePath.endsWith(File.separator)){  
	            savePath = savePath + File.separator;  
	        }  
	          
	        PrintWriter printWriter = null;  
	        BufferedReader bufferedReader = null;
	        try {  
	            printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(savePath + fileName), "utf8"));
	            String sqlPath = savePath + fileName;
				String processStr = "cmd /c c://mysqldump -h" + hostIP + " -p" + port + " -u" + userName + " -p" + password  + " "+ databaseName ;
	            System.err.println(processStr);
	            Process process = Runtime.getRuntime().exec(processStr);  
	            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), "utf8");  
	            bufferedReader = new BufferedReader(inputStreamReader);  
	            String line;  
	            while((line = bufferedReader.readLine())!= null){  
	                printWriter.println(line);  
	            }  
	            printWriter.flush(); 
	            if(process.waitFor() == 0){//0 表示线程正常终止。  
	                return true;  
	            }  
	        }catch (IOException e) {  
	            e.printStackTrace();  
	        } finally {  
	            try {  
	                if (bufferedReader != null) {  
	                    bufferedReader.close();  
	                }  
	                if (printWriter != null) {  
	                    printWriter.close();  
	                }  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        return false;  
	    }  
	    public static int restore(String hostIP,String port, String userName, String password, String databaseName,String url) throws InterruptedException {
	        try {
	            Runtime runtime = Runtime.getRuntime();
	            Process process = runtime.exec("mysql -h" + hostIP + " -P" + port + " -u" + userName + " -p" + password + " --default-character-set=UTF8 " + databaseName );
	            OutputStream outputStream = process.getOutputStream();
	            BufferedReader br = new BufferedReader(new InputStreamReader(
	                    new FileInputStream(url), "utf-8"));
	            String str = null;
	            StringBuffer sb = new StringBuffer();
	            while ((str = br.readLine()) != null) {
	                sb.append(str + "\r\n");
	            }
	            str = sb.toString();
	            // System.out.println(str);
	            OutputStreamWriter writer = new OutputStreamWriter(outputStream,
	                    "utf-8");
	            writer.write(str);
	            writer.flush();
	            outputStream.close();
	            br.close();
	            writer.close();
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	            return 1;
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	            return 1;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return 1;
	        }
			return 0;
	    } 
	}  
