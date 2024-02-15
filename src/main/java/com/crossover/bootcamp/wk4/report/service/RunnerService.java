package com.crossover.bootcamp.wk4.report.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.crossover.bootcamp.wk4.report.schedule.JobScheduler;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RunnerService implements CommandLineRunner {

    private final JobScheduler jobScheduler;

    private final ReportBuilderService reportBuilderService;

    @Override
    public void run(String... args) {
        if (!jobScheduler.schedule()) {
            // If schedule is not successful, send once and exit
            reportBuilderService.buildAndSend();
            System.exit(0);
        }
    }
}
