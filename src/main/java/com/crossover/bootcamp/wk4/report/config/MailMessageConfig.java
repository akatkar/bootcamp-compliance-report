package com.crossover.bootcamp.wk4.report.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "mail.message")
public class MailMessageConfig {

    private String from;
    private String[] to;
    private String[] cc;
    private String subject;
    private String template;
    private List<String> attachments;
    private boolean individually;

}
