package com.idun.test;

import org.apache.log4j.Logger;
import org.junit.Test;

public class LogTest {
    private static Logger logger = Logger.getLogger(LogTest.class);
    @Test
    public void main(){
        logger.info("WTF!");
    }
}
