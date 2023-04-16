package com.crossover.bootcamp.wk4.report.mail;

class EmailNotFoundException extends RuntimeException {

    EmailNotFoundException() {
        super("Email address column could not be found. Column header must contain 'email'");
    }
}
