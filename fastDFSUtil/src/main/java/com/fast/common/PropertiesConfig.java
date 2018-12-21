package com.fast.common;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @ClassName: PropertiesConfig 
 * @Description: TODO(properties文件中配置的内容) 
 * @author  Luo  
 * @date 2018年12月20日 下午4:29:50 
 *
 */
public class PropertiesConfig {
    public static String getResourceValue(String key) {
        try {
            InputStream input = PropertiesConfig.class.getResourceAsStream("/fastDFS.properties");
            Properties p = new Properties();
            p.load(input);
            input.close();
            if (!p.containsKey(key)) {
            	throw ERRORS.CONFIG_DATA_EMPTY.ERROR();
            }
            return p.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
