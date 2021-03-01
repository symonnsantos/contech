package com.symonn.contech.repository.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @AllArgsConstructor
public class ResumoConta {

    private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private LocalDate dataDaQuitacao;
    private boolean pago;
}