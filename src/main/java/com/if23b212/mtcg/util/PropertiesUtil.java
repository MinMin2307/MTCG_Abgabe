package com.if23b212.mtcg.util;

import java.util.Properties;
import java.io.InputStream;

public class PropertiesUtil {
    private static final String path = "application.properties";
    private Properties loadProperties() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
            if(inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new RuntimeException("Failed to load properties");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

    public String getProperty(String key) {
        Properties properties = loadProperties();
        if(properties != null) {
            return properties.getProperty(key);
        }
        return null;
    }
}
