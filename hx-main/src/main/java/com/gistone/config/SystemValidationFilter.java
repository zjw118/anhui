package com.gistone.config;

import com.gistone.util.HttpClientPostFs;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 *
 * @ClassName: SystemValidationFilter
 * @Description: 业务系统中自己的验证拦截过滤器
 *
 */
@WebFilter(filterName="myFilter1",urlPatterns={"/*"})
public class SystemValidationFilter implements Filter  {
    private Logger logger = Logger.getLogger(this.getClass());
    //放行路由，含头不含尾
    private String IGNORE_PARAM_NAME =

            "/api/sys/login/check," +
            "/api/sys/login/check," +
            "/api/sys/login/getToken," +
            "/api/sys/sysAppVersion/getNewEdition," +

            "/api/dcxx/dicIndexItem/getAllList," +
            "/api/dcxx/dataRenew/getRenewVer," +
            "/api/dcxx/dicIndexItem/getAllList," +
            "/api/dcxx/dataRenew/getRenewVer," +

            "/api/sys/sysAppVersion/getNewEdition," +


            "/epr/," +
            "/img/," +
            "/css/," +
            "/js/," +
            "/fonts/," +
            "/epr/index.html," +
            "/index.html," +

            "/epr/api/sys/sysAppVersion/getNewEdition," +
            "/epr/api/sys/login/check, " +
            "/epr/api/sys/login/getToken," +
            "/epr/api/dcxx/dataRenew/getRenewVer," +
            "/epr/api/dcxx/dicIndexItem/getAllList," +

            "/api/ktdb/dataRedlineRegister/import," +
            "/api/ktdb/dataRedlineRegister/exchange," +
            "/api/ktdb/lmMarkerMobile/import," +
            "/api/ktdb/lmMarkerMobile/getMarkerByCoordinate," +
            "/api/ktdb/lmMarkerMobile/exportWord," +
            "/api/ktdb/lmBoard/import," +
            "/api/ktdb/lmBoardDiagram/importBoardDiagram";


    private String FULL_PATH =
            "/epr/" +
            "" +
            "" +
            "" +
            "";



    public final Set<String> IGNORE_PAGES = new HashSet<String>();
    public final Set<String> FULL_PAGES = new HashSet<String>();

    /**
     * 请求调用时
     * @param req
     * @param rsp
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest req, ServletResponse rsp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) rsp;
        System.out.println("调用接口");
        //用户请求的uri地址
        String uri = request.getRequestURI();
//        String uri = this.parseRequestUri(request);
        System.out.println("用户请求的uri地址=="+uri);

        //判断用户请求的地址是否需要拦截，以及helper是否为null  && (helper == null)
        if (!isCanVisit(uri)) {
            //拦截
            HttpClientPostFs http = new HttpClientPostFs(response);
            http.setParameter("login","请登录");
            http.sendByPost("http://localhost:8030");//重定向的地址
            return;
        }
        //放行
        chain.doFilter(req, rsp);
    }



    /**
     *
     * @Title: parseRequestUri
     * @Description: 获取用户请求的地址
     * @param request
     * @return    设定文件
     * @return String    返回类型
     */
    private String parseRequestUri(HttpServletRequest request) {
        String uri = request.getRequestURI();
        int index = uri.indexOf('/', 1);
        return uri.substring(index);
    }



    /**
     * 初始化过滤器
     * 容器加载时（服务器启动时）
     */
    public void init(FilterConfig config){
        String[] pages = IGNORE_PARAM_NAME.split(",");
        String[] pages2 = FULL_PATH.split(",");

        if (pages2 != null) {
            for (String page2 : pages2) {
                FULL_PAGES.add(page2);
            }
        }
        if (pages != null) {
            for (String page : pages) {
                IGNORE_PAGES.add(page);
            }
        }

    }




    /**
     *
     * @Title: isCanVisit
     * @Description: 判断该请求地址是否需要拦截
     * @param url
     * @return    设定文件
     * @return boolean 返回类型  true表示用户访问的地址不需要拦截，false表示用户访问的地址需要拦截
     */
    protected boolean isCanVisit(String url){
        boolean flag = false;
        //是否直接忽略页面
        if(FULL_PAGES.contains(url)) flag = true;
        if(!flag){
            Iterator<String> ignorePages = IGNORE_PAGES.iterator();
            while(ignorePages.hasNext()){
                String page = ignorePages.next();
                if(url.startsWith(page)){
                    flag = true;
                    break;
                }
            }



        }
        return flag;
    }


    //容器销毁时（服务器关闭时）
    public void destroy() { }

}
