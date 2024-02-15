package com.crossover.bootcamp.wk4.report.util;

import java.io.File;
import java.net.URL;

public class ResourceUtil {

    public static File getResources(String relativePath) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(relativePath);
        return new File(url.getPath());
    }
}
