package com.idun.hbase;

import com.idun.hbase.exception.ErrorInfo;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import com.idun.common.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;


@Service
public class HBaseQuery {

    private final HBaseCore hBaseCore;
    private final HBaseUtil hBaseUtil;
    private final JsonUtil jsonUtil;
    private Logger logger = Logger.getLogger(HBaseQuery.class);

    @Autowired
    public HBaseQuery(HBaseCore hBaseCore){
        this.hBaseCore = hBaseCore;
        this.hBaseUtil = new HBaseUtil();
        this.jsonUtil = new JsonUtil();
    }

    private Table createHTable(String tableName){
        return this.hBaseCore.getTable(tableName);
    }

    /**
     * Query HBase data through get(row Key).
     *
     * @param tableName HBase Table name.
     * @param rowKey HBase Column family.
     * @param qualifiers qualifiers, if it is null, will return all qualifiers.
     * @return Result is returned via json.
     * */
    public String get(String tableName, String rowKey, String... qualifiers){
        Table table = createHTable(tableName);
        Get get = new Get(Bytes.toBytes(rowKey));

        Result res;
        try {
            res = table.get(get);
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return ErrorInfo.NO_RESULT.toString();
        }

        HashMap<String, String> resMap = hBaseUtil.getMethodResultFormat(res.getNoVersionMap(), qualifiers);
        // close htable.
        try {
            table.close();
        } catch (IOException e) {
            logger.error(tableName + " is null.");
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return this.jsonUtil.mapToJson(resMap);
    }

    public String scan(){
        return null;
    }

}
