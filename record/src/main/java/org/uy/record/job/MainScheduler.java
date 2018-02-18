package org.uy.record.job;

import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;
import org.uy.record.system.SystemCount;

@Component
public class MainScheduler {

    private Logger log = Logger.getLogger(MainScheduler.class);

    public MainScheduler() {
        init();
    }

    private void init(){
        JobDetail job;
        CronTrigger trigger;
        try {
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler scheduler = sf.getScheduler();

            /**
             * 重置系统参数
             * 每日0点2分触发任务
             */
            job = JobBuilder.newJob(ResetJob.class).withIdentity("resetJob","group1").build();
            trigger = TriggerBuilder.newTrigger().withIdentity("resetTrigger", "group1").
                    withSchedule(CronScheduleBuilder.cronSchedule("0 2 0 * * ?")).build();
            scheduler.scheduleJob(job, trigger);
            log.info("重置系统参数任务配置完成");

            /**
             * 发送日常报告邮件
             * 每日0点1分触发任务
             */
            job = JobBuilder.newJob(MailJob.class).withIdentity("mailJob","group1").build();
            trigger = TriggerBuilder.newTrigger().withIdentity("mailTrigger", "group1").
                    withSchedule(CronScheduleBuilder.cronSchedule("0 1 0 * * ?")).build();
            scheduler.scheduleJob(job, trigger);
            log.info("日常报告任务配置完成");

            scheduler.start();
        } catch (SchedulerException e) {
            log.error("任务调度器异常 "+e.toString());
            SystemCount.errorCount++;
        }
    }
}
