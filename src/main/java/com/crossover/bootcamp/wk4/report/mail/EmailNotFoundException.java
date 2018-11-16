package com.crossover.bootcamp.wk4.report.mail;

public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException() {
        super("Email address column could not be found. Column header must contain 'email'");
    }
}
