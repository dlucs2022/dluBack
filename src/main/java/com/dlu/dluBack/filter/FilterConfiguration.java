package com.dlu.dluBack.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @Author hcf
 * @Date 2023/4/6 15:04
 * @Description
 */

@Configuration
public class FilterConfiguration {
    @Bean
    public RegistrationBean myFilter(){
        CrossDomainFilter loginFilter = new CrossDomainFilter();
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(loginFilter);
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/**"));
        return filterRegistrationBean;
    }
}
