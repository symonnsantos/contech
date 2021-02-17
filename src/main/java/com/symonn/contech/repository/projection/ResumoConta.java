package com.symonn.contech.repository.projection;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder @Getter @Setter
public class ResumoConta {

    private Long id;
    private String descricao;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private String pessoa;
}