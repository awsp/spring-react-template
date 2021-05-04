package com.github.awsp;

import com.github.awsp.config.ApplicationConfiguration;
import com.github.awsp.config.MinioConfiguration;
import com.github.awsp.security.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationConfiguration.class, JwtProperties.class, MinioConfiguration.class})
public class ProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

}