package com.symonn.contech.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.symonn.contech.model.Usuario;
import com.symonn.contech.model.Endereco;

import java.util.List;

@Data
@Entity
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @Column(name = "nome", length = 100)
    private String nome;

    @Embedded
    private Endereco endereco;

    @NotNull
    @Column(name = "ativo")
    private Boolean ativo;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "pessoa")
    private List<Conta> contas;
}
