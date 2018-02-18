package org.uy.record.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.uy.record.system.SystemCount;

import java.util.Date;

public class ResetJob implements Job {

    private final Logger log = Logger.getLogger(ResetJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        SystemCount.loginCount = 0;
        SystemCount.errorCount = 0;
        SystemCount.warnCount = 0;

        log.info("参数已重置 "+new Date());
    }
}
