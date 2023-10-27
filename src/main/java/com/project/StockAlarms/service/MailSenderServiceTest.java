package com.project.StockAlarms.service;

import com.project.StockAlarms.config.MailConfiguration;
import com.project.StockAlarms.service.MailSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Import(com.project.StockAlarms.config.MailConfiguration.class) // Importați configurația pentru email
public class MailSenderServiceTest {

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    public void testSendEmail() {
        assertNotNull(mailSenderService);
        assertNotNull(javaMailSender);

        String to = "andreia_m31@yahoo.com";
        String subject = "Test Subject";
        String body = "Test Body";

        mailSenderService.sendNewMail(to, subject, body);

    }
}
