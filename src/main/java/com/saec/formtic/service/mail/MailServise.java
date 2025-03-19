package com.saec.formtic.service.mail;

import com.saec.formtic.controller.exam.mail.mailDTO.MailRequestDTO;
import com.saec.formtic.model.mail.MailDesigns;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class MailServise {

    private final JavaMailSender mailSender;
    private final MailDesigns mailDesigns;

    public MailServise(JavaMailSender mailSender, MailDesigns mailDesigns) {
        this.mailSender = mailSender;
        this.mailDesigns = mailDesigns;
    }

    @Async
    public void sendMail(MailRequestDTO mailRequestDTO) throws MessagingException {
        System.out.println("Sending mail to " + mailRequestDTO.getTo());
        System.out.println("Subject: " + mailRequestDTO.getSubject());
        System.out.println("Message: " + mailRequestDTO.getMessage());
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setTo(mailRequestDTO.getTo());
        helper.setSubject(mailRequestDTO.getSubject());
        helper.setText(mailRequestDTO.getMessage(), true);
        mailSender.send(mimeMessage);



    }


}
