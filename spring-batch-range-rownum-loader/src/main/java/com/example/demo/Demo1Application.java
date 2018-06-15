package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
//@ImportResource({"classpath:job-config.xml","classpath:context-config.xml"})
@ImportResource({"classpath:context-config.xml"})
public class Demo1Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Demo1Application.class, args);
	}
}
