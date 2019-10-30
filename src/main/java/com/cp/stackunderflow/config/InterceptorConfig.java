package com.cp.stackunderflow.config;

import com.cp.stackunderflow.security.ValidateUserInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Value("${baseurl}")
    private String baseURL;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        List<String> excludePatterns = new ArrayList<>();
        excludePatterns.add("/**/user/register");
        excludePatterns.add("/**/user/login");
        excludePatterns.add("/**/tags");
        excludePatterns.add("/**/swagger-ui.html/**");
        excludePatterns.add("/v2/api-docs");
        excludePatterns.add("/configuration/ui");
        excludePatterns.add("/swagger-resources/**");
        excludePatterns.add("/configuration/security");
        excludePatterns.add("/swagger-ui.html");
        excludePatterns.add("/webjars/**");

        registry.addInterceptor(getValidateUserInterceptor()).addPathPatterns("/**").excludePathPatterns(excludePatterns);
    }

    @Bean
    public ValidateUserInterceptor getValidateUserInterceptor() {
        return new ValidateUserInterceptor();
    }
}
