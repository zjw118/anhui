package com.gistone.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gistone.entity.St4SysSa;
import com.gistone.util.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 使用注解标注过滤器
 * @WebFilter将一个实现了javax.servlet.Filter接口的类定义为过滤器
 * 属性filterName声明过滤器的名称,可选
 * 属性urlPatterns指定要过滤 的URL模式,也可使用属性value来声明.(指定要过滤的URL模式是必选属性)
 */
//@WebFilter(filterName="myFilter",urlPatterns="/*")
public class MyFilter implements Filter {
	 
	//读取路径的帮助类
	@Autowired 
	public ConfigUtil configUtil;

	//封装，不需要过滤的list列表
    protected static List<String> patterns = new ArrayList<String>();

    @Override
    public void init(FilterConfig config) throws ServletException {
    	//对不需要拦截的添加到集合中
    	patterns.add("login.html");
    	patterns.add("qrcode.html");
    	patterns.add("qrcode_next.html");
    	patterns.add("plugins");
    	patterns.add("css");
    	patterns.add("imgs");
    	patterns.add("img");
    	patterns.add("images");
    	patterns.add("upload/protectObject");
    	patterns.add("js");
    	patterns.add("login");
    	patterns.add("checkCode");
    	patterns.add("qrcodeList");
    	patterns.add("imgList");
    	patterns.add("/patrol/appinterface");
    	patterns.add("apkFile");
    }
	
	@Override
	public void destroy() { }
	
	

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        //这里填写你允许进行跨域的主机ip
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        //允许的访问方法
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        //Access-Control-Max-Age 用于 CORS 相关配置的缓存
        httpServletResponse.setHeader("Access-Control-Max-Age", "7200");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        //获取当前请求的路径
        String url=httpServletRequest.getRequestURI();
        //调用方法来确认是否需要拦截
        if (isInclude(url)){
        	//放行
        	chain.doFilter(request, response);
        } else {
        	//获取当前登录用户
        	St4SysSa user = (St4SysSa) httpServletRequest.getSession().getAttribute("user");
        	//拦截后判断当前登录用户是否有效
    		if (user!=null) {
    			chain.doFilter(request, response);
    		}else {
    			//使用路由用这种拼接js进行跳转
    			PrintWriter out = response.getWriter();
				StringBuilder builder = new StringBuilder();
				builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
				builder.append("window.top.location.href=\"");
				String path = httpServletRequest.getContextPath()+"/login.html";
//				String path = configUtil.getServerPath()+"/login.html";
				builder.append(path);
				builder.append("\";</script>");
				response.setContentType("text/html;charset=utf-8");
				out.print(builder.toString());
				out.close();
				//使用Html的时候直接使用response跳转即可
    			//httpServletResponse.sendRedirect(configUtil.getServerPath()+"/login.html");
    		}
        }
        
    }

	/**
     * 是否需要过滤
     * @param url
     * @return
     */
    private boolean isInclude(String url) {
        for (String pattern : patterns) {
            if (url.contains(pattern)) {
                return true;
            }
        }
        return false;
    }
    
}
