package com.idun.hbase;

import org.apache.hadoop.hbase.util.Bytes;

import java.util.*;

class HBaseUtil {

    HashMap<String, String> getMethodResultFormat(
            NavigableMap<byte[], NavigableMap<byte[], byte[]>> res, String... qualifiers){
        HashMap<String, String> result = new HashMap<String, String>();

        // get result row key.
        String rowkey = "";
        Set<byte[]> rowKeysByte = res.keySet();
        for(byte[] b : rowKeysByte){
            rowkey = Bytes.toString(b);
        }
        result.put("rowKey", rowkey);

        // get all qualifier and value in the row.
        NavigableMap<byte[], byte[]> value = res.get(Bytes.toBytes(rowkey));
        Set<byte[]> qualifiersByte = value.keySet();
        if(qualifiers.length == 0){
            for(byte[] qb : qualifiersByte){
                String q = Bytes.toString(qb);
                result.put(q, Bytes.toString(value.get(qb)));
            }
        }else{
            for(byte[] qb : qualifiersByte){
                for(String qs : qualifiers){
                    String q = Bytes.toString(qb);
                    if(q.equals(qs)){
                        result.put(q, Bytes.toString(value.get(qb)));
                    }
                }
            }
        }
        return result;
    }
}
