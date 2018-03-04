package com.qingwenwei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//也可以在配置类上用这个注解来扫描所有的mapper文件
//但本项目本着学习的原则，选择在每一个mapper类上添加@mapper注解
//@MapperScan("com.qingwenwei.dao")
@SpringBootApplication
public class SpringBootForumApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootForumApplication.class, args);
	}
	
}