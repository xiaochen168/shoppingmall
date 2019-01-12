package com.czn.shoppingmall.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;


public class PropertiesUtil {

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private static Properties properties = null;

    static{
        String propFileName = "shoppingmall.properties";
        properties = new Properties();
        try {
            properties.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(propFileName),"UTF-8"));
        } catch (IOException e) {
            logger.error("读取配置文件异常",e);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return value.trim();
    }

    public static String getProperty(String key, String defaultValue) {
        String value = properties.getProperty(key);
        if (StringUtils.isBlank(value)) {
            value = defaultValue;
        }
        return defaultValue;
    }

}
