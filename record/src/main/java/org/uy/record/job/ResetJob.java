package org.uy.record.job;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.uy.record.system.SystemCount;

import java.util.Date;

@Component
public class ResetJob{

    private final Logger log = Logger.getLogger(ResetJob.class);

    @Scheduled(cron = "15 0 0 * * ?")
    public void execute(){

        SystemCount.loginCount = 0;
        SystemCount.errorCount = 0;
        SystemCount.warnCount = 0;

        log.info("参数已重置 "+new Date());
    }
}
