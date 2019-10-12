package com.gistone.bjhky.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
/**
 * 服务启动执行
 *
 * @author   
 * @myblog  
 * @create    
 */
@Component
@Order(value=1)
public class MyStartupRunner1 implements CommandLineRunner {
	
	
	
	public void run(String... args) throws Exception {
		System.out.println("-------------加载完成-----------");
    }
    

}