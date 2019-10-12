package com.gistone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2 {


    public List<ResponseMessage> responseMessageList() {
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        responseMessageList.add(new ResponseMessageBuilder().code(1000).message("操作成功").responseModel(new ModelRef("ResponseData")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(1001).message("参数不能为空").responseModel(new ModelRef("ResponseData")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(1002).message("参数类型错误").responseModel(new ModelRef("ResponseData")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(1003).message("服务器异常").responseModel(new ModelRef("ResponseData")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(1004).message("登录超时").responseModel(new ModelRef("ResponseData")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(1005).message("后台逻辑错误（没有对参数全面的处理）").responseModel(new ModelRef("ResponseData")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(1006).message("失败").responseModel(new ModelRef("ResponseData")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(1007).message("填写不合法").responseModel(new ModelRef("ResponseData")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(1008).message("数据重复").responseModel(new ModelRef("ResponseData")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(1009).message("请重新登录").responseModel(new ModelRef("ResponseData")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(1010).message("暂无访问权限").responseModel(new ModelRef("ResponseData")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(1011).message("服务器上没有请求的资源").responseModel(new ModelRef("ResponseData")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(1012).message("请求需要有通过HTTP认证（BASIC认证，DIGEST认证）的认证信息").responseModel(new ModelRef("ResponseData")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(1013).message("当前已是最新版本").responseModel(new ModelRef("ResponseData")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(1014).message("未按指定模板上传文件，或者文件内容不合法").responseModel(new ModelRef("ResponseData")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(1015).message("文件不存在").responseModel(new ModelRef("ResponseData")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(1016).message("用户名不存在").responseModel(new ModelRef("ResponseData")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(1017).message("密码错误").responseModel(new ModelRef("ResponseData")).build());
        return responseMessageList;
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("//**")).build().groupName("产品化接口文档V1.1").pathMapping("/")
                .apiInfo(apiInfo("全部接口", "文档中可以为整个系统所有接口", "1.1"))
                .globalResponseMessage(RequestMethod.GET, responseMessageList())
                .globalResponseMessage(RequestMethod.POST, responseMessageList());
    }


//    @Bean
//    public Docket webUserApi() {
//        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.ant("/api/user/**")).build().groupName("用户管理接口文档V1.1").pathMapping("/")
//                .globalResponseMessage(RequestMethod.POST, responseMessageList())
//                .apiInfo(apiInfo("用户管理接口文档V1.1","文档中主要为用户管理模块接口","1.1"));
//    }
//    @Bean
//    public Docket webSpaceDataApi() {
//        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.ant("/api/spaceData/**")).build().groupName("空间数据管理接口文档V1.1").pathMapping("/")
//                .globalResponseMessage(RequestMethod.POST, responseMessageList())
//                .apiInfo(apiInfo("空间数据接口文档V1.1","文档中主要为空间数据管理接口","1.1"));
//    }
//    @Bean
//    public Docket webAppApi() {
//        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.ant("/api/app/**")).build().groupName("手机端管理接口文档V1.1").pathMapping("/")
//                .globalResponseMessage(RequestMethod.POST, responseMessageList())
//                .apiInfo(apiInfo("手机端接口文档V1.1","文档中主要为手机端业务处理接口","1.1"));
//    }




    private ApiInfo apiInfo(String name, String description, String version) {
        ApiInfo apiInfo = new ApiInfoBuilder().title(name).description(description).version(version).build();
        return apiInfo;
    }

}
