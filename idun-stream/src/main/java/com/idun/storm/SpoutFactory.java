package com.idun.storm;

import com.idun.common.IdunConfiguration;
import com.idun.common.IdunDefaultProps;
import org.apache.storm.kafka.*;
import org.apache.storm.spout.SchemeAsMultiScheme;

public class SpoutFactory {
    // storm configuration.
    private static IdunConfiguration conf = IdunConfiguration.createIdunConf();

    public static KafkaSpout createKafkaSpout(String topic, String clientId){
        String zkQuorum = conf.getString("zk.quorum.host"
                , IdunDefaultProps.ZK_QUORUM_HOST.getString());
        String kafkaZkRoot = conf.getString("kafka.zk.root"
                , IdunDefaultProps.KAFKA_ZK_ROOT.getString());
        String zkStoreRoot = conf.getString("kafka.store.offset.zk.root"
                , IdunDefaultProps.KAFKA_STORE_OFFSET_ZK_ROOT.getString());
        BrokerHosts brokerHosts = new ZkHosts(zkQuorum, kafkaZkRoot);

        SpoutConfig kafkaSpoutConf = new SpoutConfig(brokerHosts, topic, zkStoreRoot, clientId);
        kafkaSpoutConf.scheme = new SchemeAsMultiScheme(new StringScheme());

        return new KafkaSpout(kafkaSpoutConf);
    }
}
