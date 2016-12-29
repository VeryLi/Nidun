package com.idun.common;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class IdunConfiguration {
    private static Logger logger = Logger.getLogger(IdunConfiguration.class);
    private HashMap<String, Object> defaultProps = new HashMap<>();
    private HashMap<String, Object> currentProps = new HashMap<>();
    private PropertiesConfiguration tool;

    public IdunConfiguration(){

    }

    private PropertiesConfiguration createTool(){
        try {
            return new PropertiesConfiguration("stream-conf.properties");
        } catch (ConfigurationException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return new PropertiesConfiguration();
        }
    }

    private Object getPropFromFile(String key, Object defaultVal){
        if(tool.getProperty(key) == null){
            return defaultVal;
        }
        return tool.getProperty(key);
    }

    public Iterator<Map.Entry<String, Object>> iterator() {
        return currentProps.entrySet().iterator();
    }

    public Iterator<Map.Entry<String, Object>> iteratorDefault() {
        return defaultProps.entrySet().iterator();
    }
}
