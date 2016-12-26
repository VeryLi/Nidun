package com.idun.hbase;

import com.idun.common.exception.DirNotContainFile;
import com.idun.common.exception.EnvVariableIsNull;

import java.io.IOException;

public class HBaseTest {
    public static void main(String[] args) throws EnvVariableIsNull, DirNotContainFile, IOException {

        HBaseCore hBaseCore = new HBaseCore();
//        JsonUtil util = new JsonUtil();
//        HBaseUtil baseUtil = new HBaseUtil();
//        Table table = hBaseCore.getTable("IdunTest");
//        Get get = new Get(Bytes.toBytes("row01"));
//        Result res = table.get(get);
//        NavigableMap<byte[], NavigableMap<byte[], byte[]>> r = res.getNoVersionMap();
//        HashMap<String, String> m = baseUtil.getMethodResultFormat(r);
//        System.out.println(util.mapToJson(m));

        HBaseQuery hq = new HBaseQuery(hBaseCore);
        System.out.println(hq.get("IdunTest", "row01"));
        System.out.println(hq.get("IdunTest", "row01", "q1"));
        System.out.println(hq.get("IdunTest", "row01", "q2"));
/*
        // create table
        HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf("IdunTest3"));
        HColumnDescriptor col = new HColumnDescriptor("cf01");
        tableDescriptor.addFamily(col);
        admin.createTable(tableDescriptor);
*/
        // get data
/*        Result result = new Result();
        Get get = new Get(Bytes.toBytes("row01"));

        try {
            HTable table = new HTable(hBaseCore.getConfig(), Bytes.toBytes("IdunTest"));
            result = table.get(get);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result.getNoVersionMap());
        String rowkey = Bytes.toString(result.getRow());
        String value = Bytes.toString(result.getValue(Bytes.toBytes("cf1"), Bytes.toBytes("q1")));
        System.out.println(rowkey + " => " + value);*/

    }
}
