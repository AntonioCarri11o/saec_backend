package com.saec.formtic.controller.exam.mail.mailDTO;

import lombok.Data;

@Data
public class MailRequestDTO {
    private String to;
    private String subject;
    private String message;
}
