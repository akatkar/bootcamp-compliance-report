package com.crossover.bootcamp.wk4.report.schedule;

import static org.mockito.BDDMockito.then;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.crossover.bootcamp.wk4.report.service.ReportBuilderService;

@ExtendWith(MockitoExtension.class)
class MailReportJobTest {
    @Mock
    private ReportBuilderService reportBuilderService;
    @InjectMocks
    private MailReportJob underTest;
    @Mock
    private JobExecutionContext jobExecutionContext;

    @Test
    void executeInternal() throws JobExecutionException {
        underTest.executeInternal(jobExecutionContext);
        then(reportBuilderService).should().buildAndSend();
    }
}
