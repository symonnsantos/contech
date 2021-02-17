package com.symonn.contech.repository;
import com.symonn.contech.model.Pessoa;

import com.symonn.contech.repository.query.pessoa.PessoaRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
