package com.estudos.analisecredito.service.impl;

import com.estudos.analisecredito.domain.Proposta;
import com.estudos.analisecredito.domain.Usuario;
import com.estudos.analisecredito.exceptions.RequestException;
import com.estudos.analisecredito.service.UsuarioService;
import com.estudos.analisecredito.service.strategy.CalculoPonto;
import com.estudos.analisecredito.utils.SmsConstante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Random;


//Colocado a ordem pois sao as que tem exceções
@Order(2)
@Component
public class PontuacaoScoreImpl implements CalculoPonto {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public int calcular(Proposta proposta) {
        int score = score();

        if (score <= 200)
            RequestException.unprocessableEntity(String.format(SmsConstante.CLIENTE_NEGATIVADO, usuarioService.buscarUsuarioPorIdRetornaNome(proposta.getUsuarioId())));

        if (score <= 400) return 150;

        if (score <= 600) return 180;

        return 220;
    }

    private int score(){
        return new Random().nextInt(0,1000);
    }
}
