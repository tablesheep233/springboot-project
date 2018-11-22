package org.table.neweims.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.table.neweims.interceptor.TokenInterceptor;

@Configuration
public class MyWebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/student").setViewName("student/main");
        registry.addViewController("/enterprise").setViewName("enterprise/main");
        registry.addViewController("/admin").setViewName("admin/main");
        registry.addViewController("/choose").setViewName("user/choose");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**");
    }
}
