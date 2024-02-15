package com.crossover.bootcamp.wk4.report.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crossover.bootcamp.wk4.report.mail.MailSender;
import com.crossover.bootcamp.wk4.report.model.SheetData;
import com.crossover.bootcamp.wk4.report.sheet.ExcelSheetReader;

@ExtendWith(MockitoExtension.class)
class ReportBuilderServiceTest {

    @Mock
    private ExcelSheetReader excelSheetReader;

    @Mock
    private MailSender mailSender;

    @InjectMocks
    private ReportBuilderService underTest;

    @Test
    void shouldBuildAndSend() throws GeneralSecurityException, IOException {
        // Given
        List<List<Object>> data = List.of (
                List.of("First Name", "Last Name", "Email Address", "Department"),
                List.of("John", "Wick",	"johnwick@gmail.com", "murder"),
                List.of("Bruce", "Lee",	"brucelee@gmail.com", "kungfu")
        );
        given(excelSheetReader.readData()).willReturn(data);
        // When
        underTest.buildAndSend();
        // Then
        then(mailSender).should().sendMail(any(SheetData.class));
    }

    @Test
    void ShouldThrowException() throws GeneralSecurityException, IOException {
        // Given
        given(excelSheetReader.readData()).willThrow(new IOException());
        // When
        underTest.buildAndSend();
        // Then
        then(mailSender).shouldHaveNoInteractions();
    }
}