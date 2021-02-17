package com.symonn.contech.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import com.symonn.contech.model.Permissao;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user", length = 100)
    private String user;

    @Column(name = "senha", length = 100)
    private String senha;

    @Column(name = "ativo")
    private Boolean ativo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_permissao", joinColumns = @JoinColumn(name = "codigo_usuario")
            , inverseJoinColumns = @JoinColumn(name = "codigo_permissao"))

    private List<Permissao> permissoes;

    @JsonIgnore
    @Transient
    public boolean isInativo(){
        return !this.ativo;
    }

}
