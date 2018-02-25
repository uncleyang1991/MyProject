package org.uy.record.tools;

import org.apache.log4j.Logger;
import org.uy.record.system.SystemCount;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatTool {

    private static SimpleDateFormat sdf = new SimpleDateFormat();

    private final static Logger log = Logger.getLogger(DateFormatTool.class);

    public static Date strToDate(String pattern,String str){
        if(pattern==null){
            sdf.applyPattern("yyyy-MM-dd");
        }else{
            sdf.applyPattern(pattern);
        }

        Date date = null;
        try{
            date = sdf.parse(str);
        }catch (ParseException e) {
            log.error("字符串转换日期格式异常 "+e.toString());
            SystemCount.errorCount++;
        }
        return date;
    }

    public static String dateToStr(String pattern,Date date){
        if(pattern==null){
            sdf.applyPattern("yyyy-MM-dd");
        }else{
            sdf.applyPattern(pattern);
        }
        return sdf.format(date);
    }
}
