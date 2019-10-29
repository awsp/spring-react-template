package com.areamode.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration {

    private final static String adminPath = "admin-center";

    @Configuration
    public static class CustomWebMvcConfigurerAdapter implements WebMvcConfigurer {

        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/" + adminPath).setViewName("redirect:/admin-center/");
            registry.addViewController("/" + adminPath + "/").setViewName("forward:/" + adminPath + "/index.html");
        }
    }

}
