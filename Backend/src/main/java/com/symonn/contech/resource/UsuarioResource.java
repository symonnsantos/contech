package com.symonn.contech.resource;

import com.symonn.contech.model.Pessoa;
import com.symonn.contech.model.Usuario;
import com.symonn.contech.repository.UsuarioRepository;
import com.symonn.contech.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioResource {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping
    public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario usuario, HttpServletResponse response){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuario));
    }

    @GetMapping
    public List<Usuario> buscaTodos(){
        return usuarioService.buscarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPeloCodigo(@PathVariable Long id){
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario != null ? ResponseEntity.ok(usuario.get()) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id){
        usuarioRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @Valid @RequestBody Usuario usuario){
        return ResponseEntity.ok(usuarioService.atualizar(id, usuario));
    }

    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setarAtivo(@PathVariable Long id, @RequestBody Boolean ativo){
        usuarioService.setarAtivo(id, ativo);
    }
}
