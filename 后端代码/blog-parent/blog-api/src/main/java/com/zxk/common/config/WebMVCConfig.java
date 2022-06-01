package com.zxk.common.config;

import com.zxk.common.interceptor.LoginInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 闫柯含
 * @date 2021年 12月 29日 下午 9:43
 * @功能说明: =>跨域问题配置
 */
@Configuration
@AllArgsConstructor
public class WebMVCConfig implements WebMvcConfigurer {

    private LoginInterceptor loginInterceptor;

    /**
     * 过滤器的配置
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginInterceptor) //将自定义的过滤器加入
                // 给文章评论需要登录
                .addPathPatterns("/comments/create/change")
                // 发布文章需要登录
                .addPathPatterns("/articles/publish");
    }

    /**
     * 跨域问题的配置
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080");
    }


}
