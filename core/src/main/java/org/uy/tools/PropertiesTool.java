package org.uy.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//读取properties文件工具类
public class PropertiesTool {

    public static Map<String,String> readFile(InputStream is){
        Map<String,String> map = new HashMap<String,String>();
        Properties properties = new Properties();
        try {
            properties.load(is);
            for(Object key:properties.keySet()){
                map.put(key.toString().trim(),properties.get(key.toString()).toString().trim());
            }
        } catch (IOException e) {
            System.err.println("读取properties出错");
            e.printStackTrace();
        }
        return map;
    }
}
