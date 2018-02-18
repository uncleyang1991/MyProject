package org.uy.record.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.uy.record.system.SystemCount;
import org.uy.record.tools.DateFormatTool;
import org.uy.record.tools.MailTool;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class MailJob implements Job{

    private final Logger log = Logger.getLogger(MailJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        String yesterday = DateFormatTool.dateToStr(null,calendar.getTime());

        String html = "<h3>Record系统 "+yesterday+" 日常汇报如下:</h3><br>" +
                "<span><font size=4>　　本日监测到<font color=blue>"+ SystemCount.warnCount+"</font>次告警，" +
                "<font color=red>"+SystemCount.errorCount+"</font>次异常，" +
                "登录<font color=green>"+SystemCount.loginCount+"</font>次。" +
                "服务自启动至今共登录"+SystemCount.totalLoginCount+"次。</font><span>";
        new MailTool().sendMail("Record系统 "+yesterday+" 日常汇报",html,
                new File[]{new File("logs"+File.separator+"record.log."+yesterday)},
                new String[]{"record."+yesterday+".log"});
        log.info("Record系统 "+yesterday+" 日常汇报已发送 "+new Date());
    }
}
