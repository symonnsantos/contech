package com.symonn.contech.model;

import lombok.*;

import com.symonn.contech.model.TipoConta;
import com.symonn.contech.model.Pessoa;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "conta")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @Column(name = "descricao", length = 100)
    private String descricao;

    @NotNull
    @Column(name = "valor", length = 15)
    private BigDecimal valor;

    @NotNull
    @Column(name = "pago")
    private Boolean pago;

    @NotNull
    @Column(name = "parcelas", length = 10)
    private int parcelas;

    @Column(name = "data_pagamento")
//    @JsonFormat(pattern =  "dd/MM/yyyy")
    private LocalDate dataPagamento;

    @Column(name = "data_vencimento")
//    @JsonFormat(pattern =  "dd/MM/yyyy")
    private LocalDate dataVencimento;

    @Column(name = "observacao", length = 100)
    private String observacao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_conta", length = 10, nullable = false)
    private TipoConta tipoConta;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_pessoa", nullable = false)
    private Pessoa pessoa;

}
