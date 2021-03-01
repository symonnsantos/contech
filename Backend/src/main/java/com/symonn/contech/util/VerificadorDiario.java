package com.symonn.contech.util;

import com.symonn.contech.resource.EmailResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component @EnableScheduling
public class VerificadorDiario {

    @Autowired
    private EmailResource emailResource;

    private final long SEGUNDO = 1000;
    private final long MINUTO = SEGUNDO * 60;
    private final long HORA = MINUTO * 60;

    @Scheduled(fixedDelay = HORA * 12)
    public void verificaPorHora() throws MessagingException {
        emailResource.sendMail();
    }

}
