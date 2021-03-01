package com.symonn.contech.resource;

import com.symonn.contech.model.Pessoa;
import com.symonn.contech.repository.PessoaRepository;
import com.symonn.contech.repository.filter.PessoaFilter;
import com.symonn.contech.repository.query.pessoa.PessoaRepositoryImp;
import com.symonn.contech.service.PessoaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoa")
public class PessoaResource {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private MessageSource messageSource;

    @PostMapping
    public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaRepository.save(pessoa));
    }

    @GetMapping
    public List<Pessoa> buscaTodos(){
        return pessoaService.buscaTodasAsPessoas();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long id){
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        return pessoa != null ? ResponseEntity.ok(pessoa.get()) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id){
        pessoaRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Pessoa> atualizar(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa){
        return ResponseEntity.ok(pessoaService.atualizar(id, pessoa));
    }

    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setarAtivo(@PathVariable Long id, @RequestBody Boolean ativo){
        pessoaService.atualizarPropAtivo(id, ativo);
    }

}
