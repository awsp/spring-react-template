package com.areamode.project.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
@Data
public class ApplicationConfiguration {
    private String foo;
    private boolean initializeDummyData;
}
