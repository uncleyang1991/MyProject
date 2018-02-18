package org.uy.record.tools;

import org.apache.log4j.Logger;

import java.util.UUID;

/**
 * ID工具类
 */
public class IdTool {

    private final static Logger log = Logger.getLogger(IdTool.class);

    /**
     * 获取一个UUID
     */
    public static String getUUID(){
        String[] arr = UUID.randomUUID().toString().split("-");
        StringBuffer sb = new StringBuffer();
        for(String str:arr){
            sb.append(str);
        }
        return sb.toString();
    }
}
