package com.symonn.contech.repository.query.conta;

import com.symonn.contech.model.Conta;
import com.symonn.contech.repository.filter.ContaFilter;
import com.symonn.contech.repository.projection.ResumoConta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContaRepositoryQuery {

    public Page<Conta> filtrar(ContaFilter lancamentoFilter, Pageable pageable);
    public Page<ResumoConta> resumo(ContaFilter contaFilter, Pageable pageable);
}
