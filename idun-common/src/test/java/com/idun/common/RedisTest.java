package com.idun.common;

import redis.clients.jedis.Jedis;

public class RedisTest {

    private Jedis client;
    private String redisServer;
    private int redisPort;
    private String redisAuth;

    private RedisTest(String redisServer, int redisPort, String redisAuth){
        this.redisServer = redisServer;
        this.redisPort = redisPort;
        this.redisAuth = redisAuth;
        connRedis();
    }

    private void connRedis(){
        client = new Jedis(redisServer, redisPort);
        client.auth(redisAuth);
    }

    private Jedis getClient(){
        return client;
    }

    public static void main(String[] args){
        RedisTest redisTest = new RedisTest("yeadun", 6379, "myredis");
        
        Jedis client = redisTest.getClient();
        System.out.println(client.get("test"));
    }
}
