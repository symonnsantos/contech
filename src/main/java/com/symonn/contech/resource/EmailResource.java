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
        DateTimeFormatter formataData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormat formataMoeda = new DecimalFormat("'R$ ' 0.##");
        StringBuilder builder = new StringBuilder();
        SimpleMailMessage message = new SimpleMailMessage();
        List<Conta> contas = contaRepository.findAll();

        builder.append("<table border=\"1\"> \n");
        builder.append("</thead> \n <tr> \n");
        builder.append("<th>Descrição</th> \n");
        builder.append("<th>Valor</th> \n");
        builder.append("<th>Data vencimento</th> \n");
        builder.append("</tr> \n </thead> \n");

        builder.append("<tbody> \n");

        for (Conta conta : contas) {
            if((conta.getDataPagamento().equals(LocalDate.now())) && !(conta.getPago())){
                message.setSubject("Ei, você tem contas a pagar hoje!");

                builder.append("<tr> \n");
                builder.append("<td> " + conta.getDescricao() + " </td> \n");
                builder.append("<td> " + formatar.formataReal(conta.getValor()) + " </td> \n");
                builder.append("<td> " + formatar.formataData(conta.getDataVencimento()) + " </td> \n");
                builder.append("</tr> \n");
            }
        }

        builder.append("<tbody> \n");
        builder.append("</tfoot> \n </table>");

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

