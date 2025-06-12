package com.estudos.analisecredito.service.impl;

import com.estudos.analisecredito.domain.Proposta;
import com.estudos.analisecredito.service.strategy.CalculoPonto;

public class PrazoPagamento implements CalculoPonto {

    @Override
    public int calcular(Proposta proposta) {
        return proposta.getPrazoPagamento() < 120 ? 80 : 0 ;
    }

}
