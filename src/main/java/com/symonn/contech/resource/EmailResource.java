package com.symonn.contech.resource;

import com.symonn.contech.ContechApplication;
import com.symonn.contech.model.Conta;
import com.symonn.contech.model.Pessoa;
import com.symonn.contech.repository.ContaRepository;
import com.symonn.contech.util.Formatadores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class EmailResource {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private Formatadores formatar;

// TODO: Gerar relatório com thymeleaf. Foi criado assim somente para testes momentâneos!

    @RequestMapping(path = "/email-send", method = RequestMethod.GET)
    public String sendMail() {
        StringBuilder builder = new StringBuilder();
        SimpleMailMessage message = new SimpleMailMessage();
        List<Conta> contas = contaRepository.findAll();

        for (Conta conta : contas) {
            if((conta.getDataPagamento().equals(LocalDate.now())) && !(conta.getPago())){
                message.setSubject("Ei, você tem contas a pagar hoje!");

                builder.append("Descrição: " + conta.getDescricao() + " | ");
                builder.append("Valor: " + formatar.formataReal(conta.getValor()) + " | ");
                builder.append("Data vencimento " + formatar.formataData(conta.getDataVencimento()) + "\n");
            }
        }

        message.setText(builder.toString());
        message.setTo("symonn.santos@gmail.com");
        message.setFrom("contech.java@gmail.com");

        try {
            mailSender.send(message);
            return "Email enviado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao enviar email.";
        }
    }
}

