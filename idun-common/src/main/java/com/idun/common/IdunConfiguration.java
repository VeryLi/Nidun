package com.idun.common;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import java.nio.file.Path;

public class IdunConfiguration extends PropertiesConfiguration{
    private static Logger logger = Logger.getLogger(IdunConfiguration.class);
    private final static String confPath = "idun-conf.properties";

    public static IdunConfiguration createIdunConf(){
        try {
            return new IdunConfiguration(confPath);
        } catch (ConfigurationException e) {
            logger.warn("configuration file is not found, so IdunConfiguration is Empty.");
            return new IdunConfiguration();
        }
    }

    public static IdunConfiguration createIdunConf(String path){
        try {
            return new IdunConfiguration(path);
        } catch (ConfigurationException e) {
            logger.warn(path + " file is not found, so IdunConfiguration is Empty.");
            return new IdunConfiguration();
        }
    }
    private IdunConfiguration(){
        super();
    }

    private IdunConfiguration(String path) throws ConfigurationException {
        super(path);
    }
}