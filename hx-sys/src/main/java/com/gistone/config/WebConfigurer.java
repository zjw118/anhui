package com.gistone.config;

import com.gistone.interceptor.SysUserLoginInterceptor;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfigurer implements WebMvcConfigurer {



//    /**
//     * 该过滤器用于实现单点登出功能，可选配置
//     * @return
//     */
//    @Bean
//    public FilterRegistrationBean sessionExpireFilter(){
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
////        registrationBean.setFilter((Filter) this.SessionExpireFilter());
//        registrationBean.setFilter(new SingleSignOutFilter());
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.setOrder(1);//启动时候的优先级
//        return registrationBean;
//    }
//
//    /**
//     * 该过滤器负责对Ticket的校验工作，必须启用它
//     * @return
//     */
//    @Bean
//    public FilterRegistrationBean cas20ProxyReceivingTicketValidationFilter(){
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(new Cas20ProxyReceivingTicketValidationFilter());
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.setOrder(2);//启动时候的优先级
//        return registrationBean;
//    }
//
//    /**
//     * 该过滤器负责用户的认证工作，必须启用它
//     * @return
//     */
//    @Bean
//    public FilterRegistrationBean authenticationFilter(){
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(new AuthenticationFilter());
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.setOrder(3);//启动时候的优先级
//        return registrationBean;
//    }
//
//
//    /**
//     * 该过滤器负责实现HttpServletRequest请求的包裹，
//     * 比如允许开发者通过HttpServletRequest的getRemoteUser()方法获得SSO登录用户的登录名，
//     * 可选配置。
//     * @return
//     */
//    @Bean
//    public FilterRegistrationBean httpServletRequestWrapperFilter(){
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(new HttpServletRequestWrapperFilter());
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.setOrder(4);//启动时候的优先级
//        return registrationBean;
//    }


 //---------------------------------------------------------------------



    /**
     * 自定义拦截器
     * @return
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SysUserLoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/fonts/**","/img/**","/css/**","/js/**")
                .excludePathPatterns("/index.html")
                .excludePathPatterns("/api/sys/login/check") //登陆接口
                .excludePathPatterns("/api/sys/login/logout") //登出接口

        ;

    }







}
