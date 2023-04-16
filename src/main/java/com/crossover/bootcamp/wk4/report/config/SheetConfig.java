package com.crossover.bootcamp.wk4.report.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "sheet")
public class SheetConfig {

    private String source;
    private String range;
    private String appName;

}
