package com.idun.common.util;

import java.util.HashMap;

public class JsonUtil {

    public String mapToJson(HashMap<String, String> map){
        String res = "[{";
        for (String key : map.keySet()) {
            res += "\"" + key + "\":\"" + map.get(key) + "\",";
        }
        res += "}]";
        res = res.replace(",}]","}]");
        return res;
    }
}
