package org.uy.record.system;

import java.util.Date;

public class SystemCount {

    //服务告警数量
    public static int warnCount = 0;

    //服务异常数量
    public static int errorCount = 0;

    //当日登录次数
    public static int loginCount = 0;

    //总登录次数
    public static int totalLoginCount = 0;

    //服务启动时间
    public static Date serverStartTime = new Date();
}
