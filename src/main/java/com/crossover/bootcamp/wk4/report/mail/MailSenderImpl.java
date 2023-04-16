package com.crossover.bootcamp.wk4.report.mail;

import com.crossover.bootcamp.wk4.report.config.MailMessageConfig;
import com.crossover.bootcamp.wk4.report.model.SheetData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
class MailSenderImpl implements MailSender{

    private final JavaMailSender mailSender;

    private final MailContentBuilder mailBuilder;

    private final MailMessageConfig mailMessageConfig;

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
                .forEach(value -> sendMail(value.get(emailHeader), data.getHeaders(), value));
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
        log.info("{} sent with subject {} to {}",
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
        log.info("{} sent with subject {} to {}",
                mailMessageConfig.getTemplate(),
                mailMessageConfig.getSubject(),
                toEmail);
    }

    private void addAttachments(MimeMessageHelper messageHelper, List<String> files) {
        files.stream()
                .map(Paths::get)
                .filter(Files::exists)
                .forEach(path -> addAttachment(messageHelper, path));
    }

    private void addAttachment(MimeMessageHelper messageHelper, Path path) {
        try{
            messageHelper.addAttachment(path.getFileName().toString(), path.toFile());
        }catch (MessagingException e) {
            log.error("{} cannot be attached", path, e);
        }
    }
}
