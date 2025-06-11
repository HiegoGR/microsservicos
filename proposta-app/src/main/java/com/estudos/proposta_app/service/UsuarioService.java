package com.estudos.proposta_app.service;

import com.estudos.proposta_app.dto.UsuarioDTO;
import com.estudos.proposta_app.entity.Usuario;
import com.estudos.proposta_app.mapper.UsuarioMapper;
import com.estudos.proposta_app.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    public UsuarioDTO salvar(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuario);
    }

    public List<UsuarioDTO> listar(){
        return usuarioRepository.findAll().stream().map(usuarioMapper::toDTO).toList();
    }

    public UsuarioDTO buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

}
