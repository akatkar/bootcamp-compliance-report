package com.crossover.bootcamp.wk4.report.mail;

import com.crossover.bootcamp.wk4.report.model.SheetData;

public interface MailSender {

    void sendMail(SheetData data);
}
