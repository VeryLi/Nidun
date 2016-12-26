package com.idun.common.util;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PropUtil {

    private Logger logger = Logger.getLogger(PropUtil.class);
    private File confFile;
    private BufferedReader confReader;

    public PropUtil(String confPath) throws IOException {
        this.confFile = new File(confPath);
        if(!this.confFile.isFile() || this.confFile == null){
            this.logger.error(confPath + " dose not exist.");
            throw new IOException(confPath + " dose not exist.");
        }
    }

    private void init(){
        try {
            this.confReader = new BufferedReader(new FileReader(this.confFile));
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean check(String line) {
        return !(line.startsWith("#") || line.equals("")) && line.split("=").length == 2;
    }

    public String getPropWithKey(String key){
        init();
        String data = null;
        try {
            while((data = this.confReader.readLine()) != null){
                if(check(data)) {
                    String k = data.split("=")[0].trim();
                    if (k.equals(key)) {
                        return data.split("=")[1].trim();
                    }
                }
            }
        } catch (IOException e) {
            this.logger.error(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                this.confReader.close();

            } catch (IOException e) {
                this.logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return data;
    }

    public Map<String, String> getAllPorps(){
        init();
        Map<String, String> data = new HashMap<String, String>();
        String temp;
        try {
            while((temp = this.confReader.readLine()) != null){
                if(check(temp)){
                    data.put(temp.split("=")[0].trim(), temp.split("=")[1].trim());
                }
            }
        } catch (IOException e) {
            this.logger.error(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                this.confReader.close();

            } catch (IOException e) {
                this.logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return data;
    }
}
