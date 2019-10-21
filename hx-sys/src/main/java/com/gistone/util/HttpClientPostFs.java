/**  
 * Title: HttpClientPostFs.java  
 * Description: 
 * Copyright: © 北京山海础石信息技术有限公司版权所有 
 * Company:www.gistone.cn 
 * @author xxh  
 * @date 2018年7月18日  
 * @version 1.0  
 */  
package com.gistone.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**  
 * Title: HttpClientPostFs 
 * Description:   seesion传递过期后返回参数配置
 * @author xxh  
 * @date 2018年7月18日  
 */
public class HttpClientPostFs {
	Map<String, String> parameter=new HashMap<String, String>();
	HttpServletResponse response;

	public HttpClientPostFs()
	{
	}
	public HttpClientPostFs(HttpServletResponse response)
	{
		this.response=response;
	}
	public void setParameter(String key,String value)
	{
		this.parameter.put(key, value);
	}
	public void sendByPost(String url) throws IOException
	{
		this.response.setContentType("text/html");
		PrintWriter out = this.response.getWriter();
		out.println(2);
		//	  out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		//	  out.println("<HTML>");
		//	  out.println(" <HEAD><TITLE>sender</TITLE></HEAD>");
		//	  out.println(" <BODY>");
		//	  out.println("<form name=\"submitForm\" action=\""+url+"\" method=\"post\">");
		//	    Iterator<String> it=this.parameter.keySet().iterator();
		//	  while(it.hasNext())
		//	  {
		//	   String key=it.next();
		//	   out.println("<input type=\"hidden\" name=\""+key+"\" value=\""+this.parameter.get(key)+"\"/>");
		//	  }
		//	  out.println("</from>");
		//	  out.println("<script>window.document.submitForm.submit();</script> ");
		//	  out.println(" </BODY>");
		//	  out.println("</HTML>");
		out.flush();
		out.close();
	}
}
