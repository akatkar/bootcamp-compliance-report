package com.crossover.bootcamp.wk4.report.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledTasks {

    @Value("${schedule.cron}")
    private String timeToSend;

    private Scheduler scheduler;

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
                log.error("{} is not valid cron expression.", timeToSend);
                System.exit(1);
            }

            try {
                JobDetail jobDetail = buildJobDetail();
                Trigger trigger = buildJobTrigger(jobDetail);
                scheduler.scheduleJob(jobDetail, trigger);
                log.info("Job scheduled as {}", timeToSend);
                return true;
            }catch (Exception e){
                log.error("Could not be scheduled for {}", timeToSend, e);
            }
        }
        log.info("will be sent once at now");
        return false;
    }
}
