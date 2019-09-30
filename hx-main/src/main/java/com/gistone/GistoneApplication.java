package com.gistone;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@MapperScan("com.gistone.mapper") // 配置dao层路径
@SpringBootApplication
public class GistoneApplication  extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(GistoneApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(GistoneApplication.class);
    }


}
