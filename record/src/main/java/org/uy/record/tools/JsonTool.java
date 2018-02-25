package org.uy.record.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.uy.record.system.SystemCount;

import java.io.IOException;
import java.util.Map;

/**
 * Json工具类
 */
public class JsonTool {

    private static ObjectMapper mapper = new ObjectMapper();

    private final static Logger log = Logger.getLogger(JsonTool.class);

    /**
     * 序列化
     */
    public static String obj2json(Object obj){
        String json = null;
        try {
            json = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("json转换异常 "+e.toString());
            SystemCount.errorCount++;
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
        } catch (IOException e) {
            log.error("json转换异常 "+e.toString());
            SystemCount.errorCount++;
        }
        return t;
    }

    /**
     *  结果json
     */
    public static String makeResultJson(boolean result,String msg){
        StringBuffer sb = new StringBuffer();
        sb.append("{\"success\":").append(result).append(",\"msg\":\"").append(msg).append("\"}");
        return sb.toString();
    }

    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass){
        if (map == null)
            return null;

        Object obj = null;

        try{
            obj = beanClass.newInstance();
            BeanUtils.populate(obj, map);
        } catch (Exception e) {
            log.error(beanClass.getName()+"实例化异常 "+e.toString());
            SystemCount.errorCount++;
        }

        return obj;
    }

    public static Map<?, ?> objectToMap(Object obj) {
        if(obj == null)
            return null;

        return new BeanMap(obj);
    }
}
