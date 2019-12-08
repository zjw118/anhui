package com.gistone.config;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class MyFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig){

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        request.setCharacterEncoding("UTF-8");
//        String uri = request.getRequestURI();
//        System.out.println(uri);
        filterChain.doFilter(servletRequest, servletResponse); //放行
    }

    @Override
    public void destroy() {

    }



}
