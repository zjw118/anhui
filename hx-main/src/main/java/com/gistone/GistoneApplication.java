package com.gistone;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.servlet.ModelAndView;

@MapperScan("com.gistone.mapper") // 配置dao层路径
@SpringBootApplication
@ServletComponentScan
public class GistoneApplication  extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(GistoneApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(GistoneApplication.class);
    }


}
