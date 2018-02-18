package org.uy.record.tools;

import org.apache.log4j.Logger;
import org.uy.record.system.SystemCount;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//读取properties文件工具类
public class PropertiesTool {

    private final static Logger log = Logger.getLogger(PropertiesTool.class);

    public static Map<String,String> readFile(InputStream is){
        Map<String,String> map = new HashMap<String,String>();
        Properties properties = new Properties();
        try {
            properties.load(is);
            for(Object key:properties.keySet()){
                map.put(key.toString().trim(),properties.get(key.toString()).toString().trim());
            }
        } catch (IOException e) {
            log.error("读取properties异常 "+e.toString());
            SystemCount.errorCount++;
        }
        return map;
    }
}
