package com.crossover.bootcamp.wk4.report.schedule;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Component
public class ScheduledTasks {

    @Value("${schedule.cron}")
    private String timeToSend;

    @Autowired
    private Scheduler scheduler;

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

    private JobDetail buildJobDetail() {

        return JobBuilder.newJob(MailReportJob.class)
                .withIdentity(UUID.randomUUID().toString(), "email-jobs")
                .withDescription("Send Email Report")
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail) {

        return TriggerBuilder.newTrigger()
                .withIdentity("cron-trigger", "email-triggers")
                .withSchedule(CronScheduleBuilder.cronSchedule(timeToSend)
                        .withMisfireHandlingInstructionFireAndProceed())
                .forJob(jobDetail)
                .build();
    }

    public boolean schedule() {
        if (!StringUtils.isEmpty(timeToSend)) {

            if(!CronExpression.isValidExpression(timeToSend)){
                LOGGER.error("{} is not valid cron expression.", timeToSend);
                System.exit(1);
            }

            try {
                JobDetail jobDetail = buildJobDetail();
                Trigger trigger = buildJobTrigger(jobDetail);
                scheduler.scheduleJob(jobDetail, trigger);
                LOGGER.info("Job scheduled as {}", timeToSend);
                return true;
            }catch (Exception e){
                LOGGER.error("Could not be scheduled for {}", timeToSend, e);
            }
        }
        LOGGER.info("will be sent once at now");
        return false;
    }
}
