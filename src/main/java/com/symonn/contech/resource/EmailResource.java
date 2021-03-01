package com.symonn.contech.resource;

import com.symonn.contech.ContechApplication;
import com.symonn.contech.model.Conta;
import com.symonn.contech.model.Pessoa;
import com.symonn.contech.repository.ContaRepository;
import com.symonn.contech.util.Formatadores;
import com.symonn.contech.util.GeradorHtml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
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

    @Autowired
    private GeradorHtml geradorHtml;

    @Autowired
    private SpringTemplateEngine templateEngine;

// TODO: Gerar relatório com thymeleaf. Foi criado assim somente para testes momentâneos!

    @RequestMapping(path = "/email-send", method = RequestMethod.GET)
    public String sendMail() throws MessagingException {

        StringBuilder builder = new StringBuilder();
        List<Conta> contas = contaRepository.findAll();

        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper( mail );
        builder.append(geradorHtml.header());

        for (Conta conta : contas) {
            if((conta.getDataPagamento().equals(LocalDate.now())) && !(conta.getPago())){
                helper.setSubject("Ei, você tem contas a pagar hoje!");

                builder.append("<tr>\n");
                builder.append("<td>" + conta.getDescricao() + "</td>\n");
                builder.append("<td>" + formatar.formataReal(conta.getValor()) + "</td>\n");
                builder.append("<td>" + formatar.formataData(conta.getDataVencimento()) + "</td>\n");
                builder.append("</tr>\n");
            }
        }

        builder.append(geradorHtml.footer());

        helper.setText(builder.toString(), true);
        helper.setTo("symonn.santos@gmail.com");
        helper.setFrom("contech.java@gmail.com");

        try {
            mailSender.send(mail);
            return "Email enviado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao enviar email.";
        }
    }
}