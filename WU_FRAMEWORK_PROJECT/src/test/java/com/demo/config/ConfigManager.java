package com.demo.config;

import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigManager — Singleton Pattern with Double-Checked Locking.
 * Reads config.properties — overridden by System properties or env variables.
 * Thread-safe: volatile + synchronized DCL.
 */
public class ConfigManager {

    private static volatile ConfigManager instance;
    private final Properties props = new Properties();

    private ConfigManager() {
        loadProperties();
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                if (instance == null) {
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }

    private void loadProperties() {
        try (InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (is != null) props.load(is);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    private String get(String key) {
        // Priority: env variable > system property > config.properties
        String envKey = key.replace(".", "_").toUpperCase();
        String envVal = System.getenv(envKey);
        if (envVal != null) return envVal;

        String sysVal = System.getProperty(key);
        if (sysVal != null) return sysVal;

        return props.getProperty(key, "");
    }

    public String  getBaseUrl()   { return get("base.url"); }
    public String  getApiUrl()    { return get("api.base.url"); }
    public String  getBrowser()   { return get("browser"); }
    public boolean isHeadless()   { return Boolean.parseBoolean(get("headless")); }
    public int     getTimeout()   { return Integer.parseInt(get("timeout")); }
}
