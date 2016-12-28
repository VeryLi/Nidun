package com.idun.storm;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

class StreamConf extends PropertiesConfiguration {

    private Logger logger = Logger.getLogger(StreamConf.class);

    private PropertiesConfiguration defaultConf ;

    public StreamConf(){
        super();
        initDefaultConf();
    }

    public StreamConf(String confPath) throws ConfigurationException {
        super(confPath);
        initDefaultConf();
    }

    private void initDefaultConf(){
        defaultConf = new PropertiesConfiguration();
        defaultConf.addProperty("kafka.zk.host", "localhost:2181");
        defaultConf.addProperty("kafka.broker.port", 9200);
        defaultConf.addProperty("kafka.topic", "YeadunTest");
    }
}
