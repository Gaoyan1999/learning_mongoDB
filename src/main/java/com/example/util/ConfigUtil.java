package com.example.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置工具类，用于加载配置文件
 */
public class ConfigUtil {
    
    /**
     * 加载配置文件
     * @return Properties 对象，如果加载失败返回空的 Properties
     */
    public static Properties loadConfig() {
        return loadConfig("config.properties");
    }
    
    /**
     * 加载指定的配置文件
     * @param configFileName 配置文件名
     * @return Properties 对象，如果加载失败返回空的 Properties
     */
    public static Properties loadConfig(String configFileName) {
        Properties config = new Properties();
        var classLoader = ConfigUtil.class.getClassLoader();
        if (classLoader == null) {
            System.err.println("Error: classLoader is null");
            return config;
        }
        
        try (InputStream input = classLoader.getResourceAsStream(configFileName)) {
            if (input == null) {
                System.err.println("Warning: " + configFileName + " not found. Using example.properties as template.");
                System.err.println("Please copy example.properties to config.properties and fill in your MongoDB credentials");
            } else {
                config.load(input);
            }
        } catch (IOException e) {
            System.err.println("Error loading configuration: " + e.getMessage());
        }
        
        return config;
    }
    
    /**
     * 获取配置属性值
     * @param config Properties 对象
     * @param key 配置键
     * @param defaultValue 默认值
     * @return 配置值，如果不存在则返回默认值
     */
    public static String getProperty(Properties config, String key, String defaultValue) {
        return config.getProperty(key, defaultValue);
    }
    
    /**
     * 获取配置属性值（必填项）
     * @param config Properties 对象
     * @param key 配置键
     * @return 配置值
     * @throws IllegalArgumentException 如果配置项不存在
     */
    public static String getRequiredProperty(Properties config, String key) {
        String value = config.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Required configuration property '" + key + "' is missing or empty");
        }
        return value;
    }
}

