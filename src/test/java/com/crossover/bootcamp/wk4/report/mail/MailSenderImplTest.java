package com.crossover.bootcamp.wk4.report.mail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.crossover.bootcamp.wk4.report.config.MailMessageConfig;
import com.crossover.bootcamp.wk4.report.mapper.SheetDataMapper;
import com.crossover.bootcamp.wk4.report.model.SheetData;

@ExtendWith(MockitoExtension.class)
class MailSenderImplTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private MailContentBuilder mailBuilder;

    @Mock
    private SheetData sheetData;

    private MailSenderImpl underTest;

    private final MailMessageConfig mailMessageConfig = createMailMessageConfig();

    @BeforeEach
    void setUp() {
        underTest = new MailSenderImpl(mailSender, mailBuilder, mailMessageConfig);
    }

    @Test
    void sendMailWithToList() {
        underTest.sendMail(sheetData);
        then(mailSender).should().send(any(MimeMessagePreparator.class));
        assertThat(mailMessageConfig.getFrom()).isEqualTo("from@test.com");
        assertThat(mailMessageConfig.getCc())
                .hasSize(2)
                .contains("test1@test.com", "test2@test.com");
    }

    @Test
    void sendMailIndividually() {
        // Given
        mailMessageConfig.setIndividually(true);
        SheetData givenSheetData = createSheetData();
        int expectedMailCount = givenSheetData.getValues().size();
        // When
        underTest.sendMail(givenSheetData);
        // Then
        then(mailSender).should(times(expectedMailCount)).send(any(MimeMessagePreparator.class));
    }

    private static MailMessageConfig createMailMessageConfig() {
        MailMessageConfig mailMessageConfig = new MailMessageConfig();
        mailMessageConfig.setTo(new String[]{"test1@test.com", "test2@test.com"});
        mailMessageConfig.setCc(new String[]{"test1@test.com", "test2@test.com"});
        mailMessageConfig.setFrom("from@test.com");
        mailMessageConfig.setSubject("subject");
        mailMessageConfig.setIndividually(false);
        mailMessageConfig.setTemplate("bootcampTemplate");
        mailMessageConfig.setAttachments(List.of("test.doc"));
        return mailMessageConfig;
    }

    private static SheetData createSheetData() {
        List<List<Object>> data = List.of (
                List.of("First Name", "Last Name", "Email Address", "Department"),
                List.of("John", "Wick",	"johnwick@gmail.com", "murder"),
                List.of("Bruce", "Lee",	"brucelee@gmail.com", "kungfu")
        );
        return SheetDataMapper.mapToSheetData(data);
    }
}