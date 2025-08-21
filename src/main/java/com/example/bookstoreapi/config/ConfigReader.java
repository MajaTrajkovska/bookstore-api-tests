package com.example.bookstoreapi.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * {@code ConfigReader} is a utility class responsible for loading application
 * configuration values from different sources in a prioritized order:
 * <ol>
 *   <li>First, it checks JVM system properties (e.g., set via {@code -Dkey=value}).</li>
 *   <li>Then, it checks environment variables (translating {@code key.name} into
 *       {@code KEY_NAME}).</li>
 *   <li>Finally, it falls back to values defined in the {@code config.properties}
 *       file located in the {@code resources} folder.</li>
 * </ol>
 * <p>
 * This allows flexible configuration across environments (local, CI/CD, Docker, etc.)
 * without modifying the source code.
 * </p>
 */
public class ConfigReader {

    /** Holds loaded configuration properties from {@code config.properties}. */
    private static final Properties properties = new Properties();

    // Static initializer to load config.properties file into memory
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

    /**
     * Retrieves the configuration value for the given key.
     * <p>
     * The lookup order is:
     * <ol>
     *   <li>System property</li>
     *   <li>Environment variable (converted to uppercase, with '.' replaced by '_')</li>
     *   <li>Property from {@code config.properties}</li>
     * </ol>
     *
     * @param key the configuration key (e.g., {@code base.url})
     * @return the resolved value, or {@code null} if not found
     */
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