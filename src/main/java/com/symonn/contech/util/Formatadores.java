package com.symonn.contech.util;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class Formatadores {

    public String formataData(LocalDate data){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        ;
        return (formatter.format(data)).toString();
    }

    public String formataReal(BigDecimal valor){
        DecimalFormat decFormat = new DecimalFormat("'R$ '0.00##");
        return (decFormat.format(valor)).toString();
    }


}
