package com.crossover.bootcamp.wk4.report.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.crossover.bootcamp.wk4.report.mail.MailSender;
import com.crossover.bootcamp.wk4.report.mapper.SheetDataMapper;
import com.crossover.bootcamp.wk4.report.model.SheetData;
import com.crossover.bootcamp.wk4.report.sheet.ExcelSheetReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportBuilderService {

    private final ExcelSheetReader excelSheetReader;

    private final MailSender mailSender;

    private SheetData read() throws IOException, GeneralSecurityException {
        return SheetDataMapper.mapToSheetData(excelSheetReader.readData());
    }

    public void buildAndSend() {
        try {
            SheetData sheetData = read();
            mailSender.sendMail(sheetData);
        } catch (Exception e){
            log.error("Error on building report", e);
        }
    }
}
