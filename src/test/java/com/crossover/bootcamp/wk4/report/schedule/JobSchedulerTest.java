package com.crossover.bootcamp.wk4.report.schedule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class JobSchedulerTest {

    private static final String TIME_TO_SEND_CRON = "0 0/2 * * * ?";
    @Mock
    private Scheduler scheduler;
    @InjectMocks
    private JobScheduler underTest;

    @Test
    void shouldSchedule() throws SchedulerException {
        ReflectionTestUtils.setField(underTest, "timeToSend", TIME_TO_SEND_CRON);
        assertThat(underTest.schedule()).isTrue();
        then(scheduler).should().scheduleJob(any(), any());
    }

    @Test
    void shouldNotSchedule() {
        assertThat(underTest.schedule()).isFalse();
        then(scheduler).shouldHaveNoInteractions();
    }

    @Test
    void shouldThrowExceptionWhenCronIsInvalid() {
        // Given
        String invalidCron = "NOT CRON";
        String expectedMessage = String.format("invalid cron:'%s'", invalidCron);
        ReflectionTestUtils.setField(underTest, "timeToSend", invalidCron);
        // When
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> underTest.schedule());
        // Then
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
        then(scheduler).shouldHaveNoInteractions();
    }

    @Test
    void shouldThrowExceptionWhenScheduleFailed() throws SchedulerException {
        // Given
        ReflectionTestUtils.setField(underTest, "timeToSend", TIME_TO_SEND_CRON);
        given(scheduler.scheduleJob(any(), any())).willThrow(new SchedulerException());
        // When
        boolean actual = underTest.schedule();
        // Then
        assertThat(actual).isFalse();
    }
}
