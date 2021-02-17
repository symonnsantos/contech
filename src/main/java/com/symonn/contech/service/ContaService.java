package com.symonn.contech.service;

import com.symonn.contech.model.Conta;
import com.symonn.contech.model.Pessoa;
import com.symonn.contech.model.TipoConta;
import com.symonn.contech.repository.ContaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    public void cadastrarConta(Conta conta, String descricao, BigDecimal valor,
                               Boolean pago, int parcelas, LocalDate dataPagamento,
                               LocalDate dataVencimento, String observacao,
                               TipoConta tipoContaEnum, Pessoa pessoa){
        Conta contaSalva = new Conta();

        contaSalva.setDataVencimento(dataVencimento);
        contaSalva.setDescricao(descricao);
        contaSalva.setValor(valor);
        contaSalva.setPago(pago);
        contaSalva.setObservacao(observacao);
        contaSalva.getPessoa().setId(pessoa.getId());
        contaSalva.setDataPagamento(dataPagamento);
        contaSalva.setTipoConta(tipoContaEnum);

        contaRepository.save(contaSalva);
    }

    public Conta atualizar(Long id, Conta conta){
        Optional<Conta> contaSalva = buscarContaPeloId(id);

        BeanUtils.copyProperties(conta, contaSalva, "id");
        return contaRepository.save(contaSalva.get());
    }

    public void setarPago(Long id, Boolean pago){
        Optional<Conta> contaSalva = buscarContaPeloId(id);
        contaSalva.get().setPago(pago);
        contaRepository.save(contaSalva.get());
    }

    public void excluirConta(Long id, Conta conta){
        Optional<Conta> contaSalva = buscarContaPeloId(id);

        contaRepository.deleteById(contaSalva.get().getId());
    }

    public void setarDataVencimento(Long id, LocalDate dataVencimento){
        Optional<Conta> contaSalva = buscarContaPeloId(id);
        contaSalva.get().setDataVencimento(dataVencimento);
        contaRepository.save(contaSalva.get());
    }

    public void setarDataPagamento(Long id, LocalDate dataPagamento){
        Optional<Conta> contaSalva = buscarContaPeloId(id);
        contaSalva.get().setDataPagamento(dataPagamento);
        contaRepository.save(contaSalva.get());
    }

    public Optional<Conta> buscarContaPeloId(Long id){
        Optional<Conta> contaSalva = contaRepository.findById(id);
        if(contaSalva == null){
            throw new EmptyResultDataAccessException(1);
        }
        return contaSalva;
    }

    public List<Conta> buscarListaContas(){
        List<Conta> contas = contaRepository.findAll();
        return contas;
    }

}
