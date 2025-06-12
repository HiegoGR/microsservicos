package com.estudos.analisecredito.service.impl;

import com.estudos.analisecredito.domain.Proposta;
import com.estudos.analisecredito.service.strategy.CalculoPonto;

import java.util.Random;

public class OutrosEmprestimosEmAndamento implements CalculoPonto {


    @Override
    public int calcular(Proposta proposta) {
        return outrosEmprestimos() ? 0 : 80;
    }

    private boolean outrosEmprestimos(){
        return new Random().nextBoolean();
    }
}
