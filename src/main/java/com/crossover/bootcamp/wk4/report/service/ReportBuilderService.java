package com.crossover.bootcamp.wk4.report.service;

import com.crossover.bootcamp.wk4.report.mail.MailSender;
import com.crossover.bootcamp.wk4.report.model.SheetData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class ReportBuilderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportBuilderService.class);

    @Autowired
    private SpreadSheetReaderService spreadSheetReaderService;

    @Autowired
    private MailSender mailSender;

    private SheetData read() throws IOException, GeneralSecurityException {
        return new SheetData(spreadSheetReaderService.readData());
    }

    public void buildAndSend(){
        try {
            SheetData sheetData = read();
            mailSender.sendMail(sheetData);
        }catch (Exception e){
            LOGGER.error("Error on building report", e);
        }
    }
}
