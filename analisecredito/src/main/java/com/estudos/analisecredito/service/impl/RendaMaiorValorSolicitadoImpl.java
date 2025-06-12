package com.estudos.analisecredito.service.impl;

import com.estudos.analisecredito.domain.Proposta;
import com.estudos.analisecredito.domain.Usuario;
import com.estudos.analisecredito.service.UsuarioService;
import com.estudos.analisecredito.service.strategy.CalculoPonto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RendaMaiorValorSolicitadoImpl implements CalculoPonto {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public int calcular(Proposta proposta) {
        return rendaMaiorValorSolicitado(proposta) ? 100 : 0;
    }

    private boolean rendaMaiorValorSolicitado(Proposta proposta){
        Usuario usuario = usuarioService.buscarUsuarioPorId(proposta.getUsuarioId());
        return usuario.getRenda() > proposta.getValorSolicitado();
    }
}
