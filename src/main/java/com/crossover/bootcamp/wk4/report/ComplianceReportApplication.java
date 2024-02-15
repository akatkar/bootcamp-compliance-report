package com.crossover.bootcamp.wk4.report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor
public class ComplianceReportApplication {

    public static void main(String[] args) {
		SpringApplication.run(ComplianceReportApplication.class, args);
	}

}

