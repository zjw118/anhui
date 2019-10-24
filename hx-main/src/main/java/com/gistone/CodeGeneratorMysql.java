package com.gistone;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @Description: mybatis-plus 3.0代码生成器 mysql版
 * 				  模板引擎是 velocity
 * 				<!-- mybatis-plus自动生成代码用，生产环境不需要此依赖 -->
			    <!-- https://mvnrepository.com/artifact/com.baomidou/mybatis-plus-generator -->
				<dependency>
				    <groupId>com.baomidou</groupId>
				    <artifactId>mybatis-plus-generator</artifactId>
				    <version>3.1.1</version>
				</dependency>
				
				<!-- mybatis-plus自动生成代码用，生产环境不需要此依赖 -->
				<!-- https://mvnrepository.com/artifact/org.apache.velocity/velocity-engine-core -->
				<dependency>
				    <groupId>org.apache.velocity</groupId>
				    <artifactId>velocity-engine-core</artifactId>
				    <version>2.1</version>
				</dependency>
				
				<!-- mysql -->
                <dependency>
                    <groupId>mysql</groupId>
                    <artifactId>mysql-connector-java</artifactId>
                    <scope>runtime</scope>
                </dependency>
				
				<!-- mybatis-plus -->
				<dependency>
			        <groupId>com.baomidou</groupId>
			        <artifactId>mybatis-plus-boot-starter</artifactId>
			        <version>3.1.1</version>
			    </dependency>
 * @author mayunju
 * @date 2019年3月11日 上午10:17:34
 */
public class CodeGeneratorMysql {
	
	/**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (!StringUtils.isEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
    	
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
//        String projectPath = System.getProperty("user.dir");

        // TODO
        // 配置自己要生成的路径
       // String projectPath = "D:\\IdeaProjects\\svn\\svn\\土壤环境信息化\\soiltrack";
        final String projectPath = "C:\\Users\\EDZ\\Desktop\\anhuiGenetor\\anhui\\";
        gc.setOutputDir(projectPath + "/src/main/java");

        // TODO
        // 配置自己的名字t
        gc.setAuthor("zhaojingwei");

        gc.setOpen(true);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);


        //TODO
        //gc 设置时间格式
        gc.setDateType(DateType.ONLY_DATE);
        //gc.setFileOverride(true);
        
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        
        dsc.setDbType(DbType.MYSQL);

        // TODO
        // 配置自己需要连接的数据库信息
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUrl("jdbc:mysql://192.168.1.37:3306/anhuiepr?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UTC");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
        pc.setParent("com.gistone");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        // String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/com/gistone/mapper"///" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();
        
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        
        // LombokModel
        strategy.setEntityLombokModel(false);
        
        strategy.setRestControllerStyle(true);
        
//        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        strategy.setInclude(scanner("表名"));
//        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        
        // 切换为 freemarker 模板引擎
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        
        mpg.execute();
    }

}
