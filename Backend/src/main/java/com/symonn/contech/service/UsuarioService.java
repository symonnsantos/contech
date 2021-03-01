package com.symonn.contech.service;

import com.symonn.contech.model.Usuario;
import com.symonn.contech.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> buscarTodos(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscaPeloId(Long id){
        Optional<Usuario> usuarioSalvo = usuarioRepository.findById(id);
        if(usuarioSalvo == null){
            throw new EmptyResultDataAccessException(1);
        }
        return usuarioSalvo;
    }

    public Usuario atualizar(Long id, Usuario usuario){
        Optional<Usuario> usuarioSalvo = usuarioRepository.findById(id);

        BeanUtils.copyProperties(usuario, usuarioSalvo.get(), "id");
        return usuarioRepository.save(usuarioSalvo.get());
    }

    public Usuario setarAtivo(Long id, Boolean ativo){
        Optional<Usuario> usuarioSalvo = usuarioRepository.findById(id);

        usuarioSalvo.get().setAtivo(ativo);
        return usuarioRepository.save(usuarioSalvo.get());
    }

    public void excluir(Long id){
        Optional<Usuario> usuarioSalvo = usuarioRepository.findById(id);
        usuarioRepository.deleteById(usuarioSalvo.get().getId());
    }
}
