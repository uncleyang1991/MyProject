package org.uy.record.job;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.uy.record.system.SystemCount;
import org.uy.record.tools.DateFormatTool;
import org.uy.record.tools.MailTool;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

@Component
public class MailJob{

    private final Logger log = Logger.getLogger(MailJob.class);

    @Scheduled(cron = "5 0 0 * * ?")
    public void execute(){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        String yesterday = DateFormatTool.dateToStr(null,calendar.getTime());
        String logFileName = "record_"+yesterday+".log";

        String html = "<h3>Record系统 "+yesterday+" 日常汇报如下:</h3><br>" +
                "<span><font size=4>　　本日监测到<font color=blue>"+ SystemCount.warnCount+"</font>次告警，" +
                "<font color=red>"+SystemCount.errorCount+"</font>次异常，" +
                "登录<font color=green>"+SystemCount.loginCount+"</font>次。" +
                "服务自启动至今共登录"+SystemCount.totalLoginCount+"次。</font><span>";
        new MailTool().sendMail("Record系统 "+yesterday+" 日常汇报",html,
                new File[]{new File("logs"+File.separator+logFileName)},
                new String[]{logFileName});
        log.info("Record系统 "+yesterday+" 日常汇报已发送 "+new Date());
    }
}
