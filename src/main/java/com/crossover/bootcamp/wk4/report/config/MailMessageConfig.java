package com.crossover.bootcamp.wk4.report.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachements) {
        this.attachments = attachements;
    }

    public boolean isIndividually() {
        return individually;
    }

    public void setIndividually(boolean individually) {
        this.individually = individually;
    }
}
