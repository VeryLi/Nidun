package com.idun.hbase;

import com.idun.common.exception.DirNotContainFile;
import com.idun.common.exception.EnvVariableIsNull;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * HBaseCore class is responsibility to create HBase configuration, connection and Admin.
 * Default, using ${HADOOP_CHONF_DIR}/core-site.xml, and ${HBASE_CONF_DIR}/hbase-site.xml config.
 * Or user can pass hadoop directory path and HBase directory path to constructor
 * , where core-site.xml and hbase-site.xml in.
 * */
@Component("hbaseAdmin")
public class HBaseCore {

    private Logger logger = Logger.getLogger(HBaseCore.class);
    private Configuration config = HBaseConfiguration.create();
    private Connection conn;

    /**
     * Construct HBaseCore by ${HADOOP_CONF_DIR} and ${HBASE_CONF_DIR}.
     * @throws EnvVariableIsNull if environment ${HADOOP_CONF_DIR} or ${HBASE_CONF_DIR} is null.
     * @throws DirNotContainFile if ${HADOOP_CONF_DIR} or ${HBASE_CONF_DIR} don't
     *                           contain hbase-site.xml or core-site.xml.
     * */
    public HBaseCore() throws EnvVariableIsNull, DirNotContainFile {
        // get environment parameter.
        String defaultCoreSitePath = System.getenv("HADOOP_CONF_DIR");
        String defaultHBaseSitePath = System.getenv("HBASE_CONF_DIR");

        if( "".equals(defaultCoreSitePath) || "".equals(defaultHBaseSitePath) )
            throw new EnvVariableIsNull("Env variable ${HADOOP_CONF_DIR} or " +
                    "${HBASE_CONF_DIR} is null.");
        addCoreSite(defaultCoreSitePath);
        addHBaseSite(defaultHBaseSitePath);
        setConfig();
        createConn();
    }

    /**
     * Construct HBaseCore by corePath, hbasePath where core-site.xml and hbase-site.xml in.
     *
     * @param corePath hadoop directory path.
     * @param hbasePath HBase directory path.
     * @throws DirNotContainFile if directories do contain hbase-site.xml or core-site.xml.
     * */
    public HBaseCore(String corePath, String hbasePath) throws DirNotContainFile {
        addCoreSite(corePath);
        addHBaseSite(hbasePath);
        setConfig();
        createConn();
    }

    /**
     * closeConn() will close connection which is connecting zookeeper.
     * */
    void closeConn(){
        try {
            this.conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Table getTable(String tableName){
        try {
            return this.conn.getTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            logger.error("table [" + tableName + "] doesn't exist.");
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    Admin getAdmin(){
        try {
            return this.conn.getAdmin();
        } catch (IOException e) {
            logger.error("connection is close or net is occur error, can't create Admin.");
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Check path is or not Valid.(Doesn't contain hbase-site.xml all are Invalid.)
     *
     * @param path the hbase-site.xml directory.
     * @throws DirNotContainFile if directory doesn't contain hbase-site.xml.
     * */
    private void addHBaseSite(String path) throws DirNotContainFile {
        if( !new File(path + "/hbase-site.xml").isFile() )
            throw new DirNotContainFile("directory [" + path+ "] doesn't contain hbase-site.xml");
        Path hbaseSite = new Path(path, "hbase-site.xml");
        this.config.addResource(hbaseSite);
    }

    /**
     * Check path is or not Valid.(Doesn't contain core-site.xml all are Invalid.)
     *
     * @param path the core-site.xml directory.
     * @throws DirNotContainFile if directory doesn't contain core-site.xml.
     * */
    private void addCoreSite(String path) throws DirNotContainFile {
        if(!new File(path + "/core-site.xml").isFile() )
            throw new DirNotContainFile("directory [" + path + "] doesn't contain core-site.xml");
        Path coreSite = new Path(path, "core-site.xml");
        this.config.addResource(coreSite);
    }

    private void createConn(){
        try {
            this.conn = ConnectionFactory.createConnection(config);
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private void setConfig(){

    }
}
