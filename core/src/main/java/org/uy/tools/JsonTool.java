package org.uy.tools;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json工具类
 */
public class JsonTool {

    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * 序列化
     */
    public static String obj2json(Object obj){
        String json = null;
        try {
           json = mapper.writeValueAsString(obj);
        } catch (Exception e) {
            System.err.println("json转换错误");
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 反序列化
     */
    public static <T>T json2obj(String json,Class<T> clazz){
        T t = null;
        try {
            t = mapper.readValue(json,clazz);
        } catch (Exception e) {
            System.err.println("json转换错误");
            e.printStackTrace();
        }
        return t;
    }
}
