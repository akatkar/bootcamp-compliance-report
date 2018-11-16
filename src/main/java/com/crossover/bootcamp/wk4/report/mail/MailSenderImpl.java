package com.crossover.bootcamp.wk4.report.mail;

import com.crossover.bootcamp.wk4.report.config.MailMessageConfig;
import com.crossover.bootcamp.wk4.report.model.SheetData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Service
class MailSenderImpl implements MailSender{

    private static final Logger LOGGER = LoggerFactory.getLogger(MailSenderImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailContentBuilder mailBuilder;

    @Autowired
    private MailMessageConfig mailMessageConfig;

    @Override
    public void sendMail(SheetData data) {

        if(mailMessageConfig.isIndividually()){
            sendMailIndividually(data);
        } else {
            sendMail(data.getHeaders(), data.getValues());
        }
    }

    private void sendMailIndividually(SheetData data) {

        String emailHeader = data.getHeaders().stream()
                .filter(s -> s.toLowerCase().contains("email"))
                .findFirst()
                .orElseGet(() -> {throw new EmailNotFoundException();});

        data.getValues()
                .stream()
                .parallel()
                .forEach(value-> {
                    sendMail(value.get(emailHeader), data.getHeaders(), value);
                });
    }

    private void sendMail(List<String> headers, List<Map<String, String>> values){

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            messageHelper.setFrom(mailMessageConfig.getFrom());
            messageHelper.setTo(mailMessageConfig.getTo());
            messageHelper.setCc(mailMessageConfig.getCc());
            messageHelper.setSubject(mailMessageConfig.getSubject());

            addAttachments(messageHelper, mailMessageConfig.getAttachments());

            String content = mailBuilder.build(mailMessageConfig.getTemplate(), headers, values);
            messageHelper.setText(content, true);
        };
        mailSender.send(messagePreparator);
        LOGGER.info("{} sent with subject {} to {}",
                mailMessageConfig.getTemplate(),
                mailMessageConfig.getSubject(),
                mailMessageConfig.getTo());
    }

    private void sendMail(String toEmail, List<String> headers, Map<String, String> value){

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            messageHelper.setFrom(mailMessageConfig.getFrom());
            messageHelper.setTo(toEmail);
            messageHelper.setCc(mailMessageConfig.getCc());
            messageHelper.setSubject(mailMessageConfig.getSubject());

            addAttachments(messageHelper, mailMessageConfig.getAttachments());

            String content = mailBuilder.build(mailMessageConfig.getTemplate(), headers, value);
            messageHelper.setText(content, true);
        };
        mailSender.send(messagePreparator);
        LOGGER.info("{} sent with subject {} to {}",
                mailMessageConfig.getTemplate(),
                mailMessageConfig.getSubject(),
                toEmail);
    }

    private void addAttachments(MimeMessageHelper messageHelper, List<String> files) {

        files.stream()
                .map(Paths::get)
                .filter(Files::exists)
                .forEach(path -> {
                    try{
                        messageHelper.addAttachment(path.getFileName().toString(), path.toFile());
                    }catch (Exception e) {
                        LOGGER.error("{} cannot be attached", path, e);
                    }
                });
    }
}
