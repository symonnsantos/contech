package com.symonn.contech.repository.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class ContaFilter {

    private String descricao;

    private LocalDate dataVencimentoDe;

    private LocalDate dataVencimentoAte;

    private LocalDate dataPagamentoDe;

    private LocalDate dataPagamentoAte;

    private Boolean pago;
}
