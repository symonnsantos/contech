package com.symonn.contech.resource;

import com.symonn.contech.model.Conta;
import com.symonn.contech.model.Usuario;
import com.symonn.contech.repository.ContaRepository;
import com.symonn.contech.repository.filter.ContaFilter;
import com.symonn.contech.repository.projection.ResumoConta;
import com.symonn.contech.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
@RequestMapping("/contas")
public class ContaResource {

    @Autowired
    private ContaService contaService;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping
    public ResponseEntity<Conta> criar(@Valid @RequestBody Conta conta, HttpServletResponse response){
        return ResponseEntity.status(HttpStatus.CREATED).body(contaRepository.save(conta));
    }

    @GetMapping
    public List<Conta> buscaTodos(){
        return contaService.buscaTodas();
    }

    @GetMapping(params = "resumo")
    public Page<ResumoConta> resumir(ContaFilter contaFilter, Pageable pageable){
        return contaRepository.resumo(contaFilter, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> buscarPeloCodigo(@PathVariable Long id){
        Optional<Conta> conta = contaRepository.findById(id);
        return conta != null ? ResponseEntity.ok(conta.get()) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id){
        contaRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conta> atualizar(@PathVariable Long id, @Valid @RequestBody Conta conta){
        return ResponseEntity.ok(contaService.atualizar(id, conta));
    }
}
