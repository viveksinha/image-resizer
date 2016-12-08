package com.opentable.transformers.configurer;

import com.opentable.transformers.interceptor.ApplicationInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Vivek Wiki
 */
@Configuration
@EnableWebMvc
public class ApplicationConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor());
    }

    private ApplicationInterceptor interceptor() {
        return new ApplicationInterceptor();
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        multipartResolver.setMaxUploadSize(81920);
        multipartResolver.setDefaultEncoding("utf-8");
        return multipartResolver;
    }

}
