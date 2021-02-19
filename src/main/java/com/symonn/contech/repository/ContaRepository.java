package com.symonn.contech.repository;
import com.symonn.contech.model.Conta;

import com.symonn.contech.repository.query.conta.ContaRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long>, ContaRepositoryQuery {

}
