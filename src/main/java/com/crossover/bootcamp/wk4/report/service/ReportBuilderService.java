package com.crossover.bootcamp.wk4.report.service;

import com.crossover.bootcamp.wk4.report.mail.MailSender;
import com.crossover.bootcamp.wk4.report.model.SheetData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportBuilderService {

    private final SpreadSheetReaderService spreadSheetReaderService;

    private final MailSender mailSender;

    private SheetData read() throws IOException, GeneralSecurityException {
        return new SheetData(spreadSheetReaderService.readData());
    }

    public void buildAndSend(){
        try {
            SheetData sheetData = read();
            mailSender.sendMail(sheetData);
        }catch (Exception e){
            log.error("Error on building report", e);
        }
    }
}
