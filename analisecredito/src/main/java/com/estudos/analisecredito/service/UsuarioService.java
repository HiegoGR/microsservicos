package com.estudos.analisecredito.service;

import com.estudos.analisecredito.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UsuarioService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${propostaapp.usuario.api.url}")
    private String urlBasePropostaAppUsuario;


    public Usuario buscarUsuarioPorId(Long id) {
        String url = urlBasePropostaAppUsuario + id;
        return restTemplate.getForObject(url, Usuario.class);
    }

    public String buscarUsuarioPorIdRetornaNome(Long id) {
        String url = urlBasePropostaAppUsuario + id;
        return  restTemplate.getForObject(url, Usuario.class).getNome();
    }
}
