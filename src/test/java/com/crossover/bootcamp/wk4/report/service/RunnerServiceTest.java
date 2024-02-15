package com.crossover.bootcamp.wk4.report.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crossover.bootcamp.wk4.report.schedule.JobScheduler;

@ExtendWith(MockitoExtension.class)
class RunnerServiceTest {
    @Mock
    private JobScheduler jobScheduler;

    @Mock
    private ReportBuilderService reportBuilderService;

    @InjectMocks
    private RunnerService underTest;

    @Test
    void runScheduled() {
        given(jobScheduler.schedule()).willReturn(true);
        underTest.run();
        then(reportBuilderService).shouldHaveNoInteractions();
    }

}
