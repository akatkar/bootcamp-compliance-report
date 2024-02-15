package com.crossover.bootcamp.wk4.report.sheet;

import java.io.File;
import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FileResourceResolver {

    private final ApplicationContext context;

    public File resolveFile(String url) throws IOException {
        return context.getResource(url).getFile();
    }
}
