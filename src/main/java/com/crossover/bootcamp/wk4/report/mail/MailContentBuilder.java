package com.crossover.bootcamp.wk4.report.mail;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailContentBuilder {

    private final TemplateEngine templateEngine;

    String build(String template, List<String> headers, List<Map<String,String>> values) {
        Context context = new Context();
        context.setVariable("headers", headers);
        context.setVariable("values", values);
        return templateEngine.process(template, context);
    }

    String build(String template, List<String> headers, Map<String,String> value) {
        Context context = new Context();
        context.setVariable("headers", headers);
        context.setVariable("value", value);
        return templateEngine.process(template, context);
    }
}
