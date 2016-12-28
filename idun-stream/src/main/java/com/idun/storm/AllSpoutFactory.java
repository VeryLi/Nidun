package com.idun.storm;

public class AllSpoutFactory {
    // storm configuration.
    private final String zkStormRoot = "/storm";

    // Kafka spout configuration.
    private String zkServer;
    private String topic;
    private Integer kafkaPort;

    private AllSpoutFactory(String zkServer, String topic, int kafkaPort){
        this.zkServer = zkServer;
        this.topic = topic;
        this.kafkaPort = kafkaPort;
    }
}
