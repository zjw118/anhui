package com.gistone.config;

import com.gistone.interceptor.SysUserLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zf1017@foxmail.com
 * @date 2019/7/25 0025 10:27
 * @description
 */

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    /**
     * 自己定义的拦截器类
     * @return
     */
    @Bean
    SysUserLoginInterceptor sysUserLoginInterceptor() {
        return new SysUserLoginInterceptor();
    }
    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sysUserLoginInterceptor()).addPathPatterns("/**").
                excludePathPatterns("**/swagger-ui.html").
//                excludePathPatterns("/epr/api/sys/login/check").
                excludePathPatterns("/api/sys/login/check").
                excludePathPatterns("/api/sys/login/check").
                excludePathPatterns("/index.html").
//                excludePathPatterns("/epr/index.html").
                excludePathPatterns("/img/**").
                excludePathPatterns("/css/**").
                excludePathPatterns("/js/**").
                excludePathPatterns("/fonts/**").
                excludePathPatterns("/index.html").
                excludePathPatterns("/api/sys/login/getToken").
//                excludePathPatterns("/epr/api/sys/login/getToken").
                excludePathPatterns("/api/dcxx/dataRenew/getRenewVer").
//                excludePathPatterns("/epr/api/dcxx/dataRenew/getRenewVer").
                excludePathPatterns("/api/dcxx/dicIndexItem/getAllList").
//                excludePathPatterns("/epr/api/dcxx/dicIndexItem/getAllList").
                excludePathPatterns("/api/sys/login/getToken").
                excludePathPatterns("/api/dcxx/dataRenew/getRenewVer").
                excludePathPatterns("/api/dcxx/dicIndexItem/getAllList").
                excludePathPatterns("/api/sys/sysAppVersion/getNewEdition").
//                excludePathPatterns("/epr/api/sys/sysAppVersion/getNewEdition").
                excludePathPatterns("/api/sys/sysAppVersion/getNewEdition").
                excludePathPatterns("/api/ktdb/lmMarkerMobile/import").
                excludePathPatterns("/api/ktdb/dataRedlineRegister/import").
                excludePathPatterns("/api/ktdb/dataRedlineRegister/exchange").
                excludePathPatterns("/api/ktdb/lmMarkerMobile/getMarkerByCoordinate").
                excludePathPatterns("/api/ktdb/lmMarkerMobile/exportWord").
                excludePathPatterns("/api/ktdb/lmBoard/import").
                excludePathPatterns("/api/ktdb/lmBoardDiagram/importBoardDiagram");

    }

}
