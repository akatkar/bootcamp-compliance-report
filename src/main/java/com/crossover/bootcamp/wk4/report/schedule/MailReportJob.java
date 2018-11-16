package com.crossover.bootcamp.wk4.report.schedule;

import com.crossover.bootcamp.wk4.report.service.ReportBuilderService;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class MailReportJob extends QuartzJobBean {

    @Autowired
    private ReportBuilderService reportBuilderService;

    @Override
    protected void executeInternal(org.quartz.JobExecutionContext jobExecutionContext) throws JobExecutionException {

        reportBuilderService.buildAndSend();
    }
}
