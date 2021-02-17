package com.symonn.contech.repository.query.pessoa;

import com.symonn.contech.model.Pessoa;
import com.symonn.contech.repository.filter.PessoaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PessoaRepositoryQuery {
    public Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable);
}
