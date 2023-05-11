package com.dlu.dluBack.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author hcf
 * @Date 2023/4/26 14:59
 * @Description
 */
@Configuration
public class AvatarVirtualConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")//前端url访问的路径，若有访问前缀，在访问时添加即可，这里不需添加。
                .addResourceLocations("file:E:/java_project/dlu_all/dluBack/ftpfile/static/datasets/");//映射的服务器存放图片目录。
    }
}
