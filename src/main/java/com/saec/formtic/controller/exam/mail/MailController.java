package com.saec.formtic.controller.exam.mail;

import com.saec.formtic.controller.exam.mail.mailDTO.MailRequestDTO;
import com.saec.formtic.service.mail.MailServise;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/mail")
@CrossOrigin({"*"})
public class MailController {
    private final MailServise mailServise;

    public MailController(MailServise mailServise) {
        this.mailServise = mailServise;
    }

    @PostMapping("/basic")
    public void sendMail(@RequestBody MailRequestDTO mailRequestDTO) throws MessagingException {
        System.out.println("Sending mail to " + mailRequestDTO.getTo());
        System.out.println("Subject: " + mailRequestDTO.getSubject());
        System.out.println("Message: " + mailRequestDTO.getMessage());
        mailServise.sendMail(mailRequestDTO);
    }
}
