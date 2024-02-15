package com.crossover.bootcamp.wk4.report.schedule;

import java.util.UUID;

import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class JobScheduler {

    @Value("${schedule.cron}")
    private String timeToSend;

    private final Scheduler scheduler;

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
        if (StringUtils.hasText(timeToSend)) {

            if(!CronExpression.isValidExpression(timeToSend)){
                log.error("{} is not valid cron expression.", timeToSend);
                throw new IllegalArgumentException("invalid cron:'" + timeToSend + "'");
            }

            try {
                JobDetail jobDetail = buildJobDetail();
                Trigger trigger = buildJobTrigger(jobDetail);
                scheduler.scheduleJob(jobDetail, trigger);
                log.info("Job scheduled as {}", timeToSend);
                return true;
            } catch (SchedulerException e){
                log.error("Could not be scheduled for {}", timeToSend, e);
            }
        }
        log.info("will be sent once at now");
        return false;
    }
}
