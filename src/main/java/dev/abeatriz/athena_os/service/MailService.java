package dev.abeatriz.athena_os.service;

import dev.abeatriz.athena_os.dto.mail.MailSendDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String sender;

    @Async
    public void send(MailSendDto json) {
        System.out.println(json);
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(sender);
        mailMessage.setTo(json.to());
        mailMessage.setText(json.content());
        mailMessage.setSubject(json.subject());

        javaMailSender.send(mailMessage);

        System.out.println("Email enviado");
    }


}
