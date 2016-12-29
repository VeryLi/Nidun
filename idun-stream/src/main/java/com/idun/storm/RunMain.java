package com.idun.storm;

import org.apache.log4j.Logger;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.bolt.KafkaBolt;
import org.apache.storm.testing.TestGlobalCount;
import org.apache.storm.testing.TestWordCounter;
import org.apache.storm.testing.TestWordSpout;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class RunMain {

    private static Logger logger = Logger.getLogger(RunMain.class);

    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("1", new TestWordSpout(true), 5);
        builder.setSpout("2", new TestWordSpout(true), 3);
        builder.setBolt("3", new TestWordCounter(), 3)
                .fieldsGrouping("1", new Fields("word"))
                .fieldsGrouping("2", new Fields("word"));
        builder.setBolt("4", new TestGlobalCount())
                .globalGrouping("1");

//
//        builder.setSpout("1", SpoutFactory.createKafkaSpout("YeadunTest", "yeadun-test"), 2);
//        builder.setBolt("2", new TestWordCounter(), 2)
//                .fieldsGrouping("1", new Fields("word"));
        logger.info("this is a test.");

        Map<String, Object> conf = new HashMap<>();
//        conf.put(Config.TOPOLOGY_WORKERS, 4);
        conf.put(Config.TOPOLOGY_DEBUG, true);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("mytopology", conf, builder.createTopology());
        Utils.sleep(10000);
        cluster.shutdown();
    }
}
