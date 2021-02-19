package com.symonn.contech.repository.query.conta;

import com.symonn.contech.model.Conta;
import com.symonn.contech.repository.filter.ContaFilter;
import com.symonn.contech.repository.projection.ResumoConta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ContaRepositoryImpl implements ContaRepositoryQuery {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Conta> filtrar(ContaFilter contaFilter, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Conta> criteria = builder.createQuery(Conta.class);

        Root<Conta> root = criteria.from(Conta.class);

        Predicate[] predicates = criarRestricoes(contaFilter, builder, root);

        criteria.where(predicates);

        TypedQuery<Conta> query = entityManager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<Conta>(query.getResultList(), pageable, (Long) total(contaFilter));
    }

    @Override
    public Page<ResumoConta> resumo(ContaFilter contaFilter, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ResumoConta> criteria = builder.createQuery(ResumoConta.class);
        Root<Conta> root = criteria.from(Conta.class);

        criteria.select(builder.construct(ResumoConta.class
                , root.get("id")
                , root.get("descricao")
                , root.get("valor")
                , root.get("dataVencimento")
                , root.get("dataPagamento")
                , root.get("dataDaQuitacao")
                , root.get("pago")
        ));

        Predicate[] predicates = criarRestricoes(contaFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<ResumoConta> query = entityManager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<ResumoConta>(query.getResultList(), pageable, total(contaFilter));
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistroPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistroPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistroPorPagina);
    }

    private Predicate[] criarRestricoes(ContaFilter contaFilter, CriteriaBuilder builder, Root<Conta> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotEmpty(contaFilter.getDescricao())) {
            predicates.add(builder.like(builder.lower(root.get("descricao")), "%" + contaFilter.getDescricao().toLowerCase() + "%"));
        }

        //Filtro data de pagamento
        if(contaFilter.getDataPagamento() != null){
            predicates.add(
                    builder.equal(root.get("dataPagamento"), contaFilter.getDataPagamento()));
        }

        //Filtro data da quitação
        if(contaFilter.getDataDaQuitacao() != null){
            predicates.add(
                    builder.equal(root.get("dataDaQuitacao"), contaFilter.getDataDaQuitacao()));
        }

        //Filtro data de vencimento - de => até
        if(contaFilter.getDataVencimentoDe() != null){
            predicates.add(
                    builder.greaterThanOrEqualTo(root.get("dataVencimento"), contaFilter.getDataVencimentoDe()));
        }
        if(contaFilter.getDataVencimentoAte() != null){
            predicates.add(
                    builder.lessThanOrEqualTo(root.get("dataVencimento"), contaFilter.getDataVencimentoAte()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private Long total(ContaFilter contaFilter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Conta> root = criteria.from(Conta.class);

        Predicate[] predicates = criarRestricoes(contaFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));

        return entityManager.createQuery(criteria).getSingleResult();
    }

}
