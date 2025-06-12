package com.estudos.analisecredito.service.impl;

import com.estudos.analisecredito.domain.Proposta;
import com.estudos.analisecredito.exceptions.RequestException;
import com.estudos.analisecredito.service.UsuarioService;
import com.estudos.analisecredito.service.strategy.CalculoPonto;
import com.estudos.analisecredito.utils.SmsConstante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Random;

// faz com que define a ordem em que seja gerado a lista por exemplo
// NomeNegativadoImpl em 1 e PontuacaoScoreImpl em 2 o restante o spring organiza, caso nao
// tenha essa anotação o proprio spring organiza
//Colocado a ordem pois sao as que tem exceções
@Order(1)
@Component
public class NomeNegativadoImpl implements CalculoPonto {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public int calcular(Proposta proposta) {

        if(nomeNegativado())
            RequestException.unprocessableEntity(String.format(String.format(SmsConstante.CLIENTE_NEGATIVADO, usuarioService.buscarUsuarioPorIdRetornaNome(proposta.getUsuarioId()))));

        return 100;
    }


    // metodo que retorna true/false aleatorio
    private boolean nomeNegativado(){
        return new Random().nextBoolean();
    }
}
