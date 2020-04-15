package com.graduation.compusinfo.display.config;

import com.graduation.compusinfo.display.common.LoginHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zzhengkai
 * @date 2019/12/4 19:18
 */
@Configuration
public class WebMvcConfigurers implements WebMvcConfigurer {
    @Autowired
    private LoginHandlerInterceptor loginHandlerInterceptor;

    /** 这个方法是用来配置静态资源的，比如html，js，css，等等*/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
        /*放行swagger*/
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginHandlerInterceptor).addPathPatterns("/comment/**","/admin/**","/replyComment/**")  //addPathPatterns("/**")
                .excludePathPatterns("/","/#","/comment/reply/get","/static/**","/admin/login","/campus/register","/campus/login")
                .excludePathPatterns("/css/**","/js/**","/images/**")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**","/swagger-ui.htm/**","/swagger-ui.html/**");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//admin类访问
        registry.addViewController("/").setViewName("admin-login");
        registry.addViewController("/admin/login").setViewName("admin-login");
        registry.addViewController("/campus/articleslist").setViewName("articleslist");
        registry.addViewController("/admin/index").setViewName("admin-index");
        registry.addViewController("/admin/release").setViewName("admin-release");
        registry.addViewController("/admin/commentManage").setViewName("admin-content");
        registry.addViewController("/admin/contentData").setViewName("admin-contentdata");
    }

}
