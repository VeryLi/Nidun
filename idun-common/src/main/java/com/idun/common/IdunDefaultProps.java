package com.idun.common;

public enum IdunDefaultProps {
    ZK_QUORUM_HOST("localhost:2181"),
    kAFKA_BROKER_PORT(9092),
    KAFKA_STORE_OFFSET_ZK_ROOT("/storm/kafka"),
    KAFKA_ZK_ROOT("/brokers");

    private Object value;
    IdunDefaultProps(Object value){
        this.value = value;
    }

    public Integer getInt(){
        return (int)this.value;
    }

    public String getString(){
        return (String) this.value;
    }

    public boolean getBoolean(){
        return (boolean) this.value;
    }
}
