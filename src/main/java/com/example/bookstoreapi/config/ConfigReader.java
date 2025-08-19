package com.example.bookstoreapi.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader 
{
    private static final Properties properties = new Properties();
    static {
       try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found in resources folder");
            }
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load config.properties", ex);
        }
    }

    public static String get(String key) {
        // 1. Check system property
        String sysProp = System.getProperty(key);
        if (sysProp != null) return sysProp;

        // 2. Check environment variable (convert key to uppercase and replace '.' with '_')
        String envVar = System.getenv(key.replace('.', '_').toUpperCase());
        if (envVar != null) return envVar;

        // 3. Fallback to config.properties
        return properties.getProperty(key);
    }
}