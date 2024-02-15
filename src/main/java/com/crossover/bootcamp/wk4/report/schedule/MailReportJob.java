package com.crossover.bootcamp.wk4.report.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.crossover.bootcamp.wk4.report.service.ReportBuilderService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MailReportJob extends QuartzJobBean {

    private final ReportBuilderService reportBuilderService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        reportBuilderService.buildAndSend();
    }
}
