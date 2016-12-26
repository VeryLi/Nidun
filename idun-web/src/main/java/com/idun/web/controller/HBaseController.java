package com.idun.web.controller;

import com.idun.hbase.HBaseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HBaseController {

    private final HBaseQuery hbaseQuery;

    @Autowired
    public HBaseController(HBaseQuery hbaseQuery) {
        this.hbaseQuery = hbaseQuery;
    }

    @RequestMapping(value = "/a", method = RequestMethod.GET)
    public
    @ResponseBody String test() {
        return hbaseQuery.get("IdunTest", "row01");
    }

    @RequestMapping(value = "/b", method = RequestMethod.GET)
    public
    @ResponseBody String test1() {
        return hbaseQuery.get("IdunTest", "row01", "q1");
    }

    @RequestMapping(value = "/c", method = RequestMethod.GET)
    public
    @ResponseBody String test2() {
        return hbaseQuery.get("IdunTest", "row01", "q2");
    }

}
