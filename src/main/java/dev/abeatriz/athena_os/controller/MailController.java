package dev.abeatriz.athena_os.controller;

import dev.abeatriz.athena_os.dto.mail.MailSendDto;
import dev.abeatriz.athena_os.service.MailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping
    public String send(@RequestBody @Valid MailSendDto json) {
        System.out.println("send");
        mailService.send(json);
        return "Email enviado";
    }

}
