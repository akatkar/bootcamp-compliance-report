package com.crossover.bootcamp.wk4.report.service;

import com.crossover.bootcamp.wk4.report.mail.MailSender;
import com.crossover.bootcamp.wk4.report.model.SheetData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class ReportBuilderService implements CommandLineRunner {

    @Autowired
    private SpreadSheetReaderService spreadSheetReaderService;

    @Autowired
    private MailSender mailSender;

    private SheetData read() throws IOException, GeneralSecurityException {
        return new SheetData(spreadSheetReaderService.readData());
    }

    @Override
    public void run(String... args) throws Exception {

        SheetData sheetData = read();
        mailSender.sendMail(sheetData);
    }
}
