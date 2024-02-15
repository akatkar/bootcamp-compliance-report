package com.crossover.bootcamp.wk4.report;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class ComplianceReportApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		ComplianceReportApplication app = applicationContext.getBean(ComplianceReportApplication.class);
		assertThat(app).isNotNull();
	}

}
