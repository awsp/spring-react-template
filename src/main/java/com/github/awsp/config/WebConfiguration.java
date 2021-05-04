package com.github.awsp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final static String adminPath = "admin-center";

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/" + adminPath).setViewName("redirect:/" + adminPath + "/");
        registry.addViewController("/" + adminPath + "/").setViewName("forward:/" + adminPath + "/index.html");
    }

}