package com.symonn.contech.service;

import com.symonn.contech.model.Pessoa;
import com.symonn.contech.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa atualizar(Long id, Pessoa pessoa){

        Optional<Pessoa> pessoaSalva = buscarPeloId(id);

        BeanUtils.copyProperties(pessoa, pessoaSalva.get(), "id");
        return pessoaRepository.save(pessoaSalva.get());
    }

    public void atualizarPropAtivo(Long id, Boolean ativo){
        Optional<Pessoa> pessoaSalva = buscarPeloId(id);
        pessoaSalva.get().setAtivo(ativo);
        pessoaRepository.save(pessoaSalva.get());
    }

    public void excluirPessoa(Long id){
        Optional<Pessoa> pessoaSalva = buscarPeloId(id);
        pessoaRepository.deleteById(pessoaSalva.get().getId());
    }

    public Optional<Pessoa> buscarPeloId(Long id){
        Optional<Pessoa> pessoaSalva = pessoaRepository.findById(id);
        if (pessoaSalva == null){
            throw new EmptyResultDataAccessException(1);
        }

        return pessoaSalva;
    }

    public List<Pessoa> buscaTodasAsPessoas(){
        return pessoaRepository.findAll();
    }


}
