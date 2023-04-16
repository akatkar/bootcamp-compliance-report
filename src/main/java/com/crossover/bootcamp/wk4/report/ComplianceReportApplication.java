package com.crossover.bootcamp.wk4.report;

import com.crossover.bootcamp.wk4.report.schedule.ScheduledTasks;
import com.crossover.bootcamp.wk4.report.service.ReportBuilderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor
public class ComplianceReportApplication implements CommandLineRunner {

    private final ScheduledTasks scheduledTasks;

    private final ReportBuilderService reportBuilderService;


    public static void main(String[] args) {
		SpringApplication.run(ComplianceReportApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {

        if (!scheduledTasks.schedule()){
            // If schedule is not successful, send once and exit
            reportBuilderService.buildAndSend();
            System.exit(0);
        }
    }
}
