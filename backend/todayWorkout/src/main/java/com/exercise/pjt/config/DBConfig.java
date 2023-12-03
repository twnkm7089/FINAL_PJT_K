package com.exercise.pjt.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.exercise.pjt.model.dao")
public class DBConfig {
	
}
