package com.saec.formtic.controller.exam.mail;

import com.saec.formtic.controller.exam.mail.mailDTO.MailRequestDTO;
import com.saec.formtic.service.mail.MailServise;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
@CrossOrigin({"*"})
public class MailController {
    @Autowired
    private MailServise mailServise;

    @PostMapping("/basic")
    public void sendMail(@RequestBody MailRequestDTO mailRequestDTO) throws MessagingException {
        System.out.println("Sending mail to " + mailRequestDTO.getTo());
        System.out.println("Subject: " + mailRequestDTO.getSubject());
        System.out.println("Message: " + mailRequestDTO.getMessage());
        mailServise.sendMail(mailRequestDTO);
    }
}
