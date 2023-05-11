package com.dlu.dluBack;

import com.dlu.dluBack.filter.CrossDomainFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@MapperScan(basePackages = "com.dlu.dluBack.mapper")
public class DluBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(DluBackApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<CrossDomainFilter> crossDomainFilter(){
		FilterRegistrationBean<CrossDomainFilter> filter = new FilterRegistrationBean<>();
		filter.setFilter(new CrossDomainFilter());
		filter.addUrlPatterns("/*");
		filter.setOrder(1);
		return filter;
	}
}
