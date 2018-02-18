package org.uy.record.interceptors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(new LoginInterceptor());
        // 拦截配置
        addInterceptor.addPathPatterns("/record/**");
        // 排除配置
        addInterceptor.excludePathPatterns("/record/error");
        addInterceptor.excludePathPatterns("/record/main/login.do");
        addInterceptor.excludePathPatterns("/record/main/checkLogin.do");
        addInterceptor.excludePathPatterns("/record");

        super.addInterceptors(registry);
    }
}
