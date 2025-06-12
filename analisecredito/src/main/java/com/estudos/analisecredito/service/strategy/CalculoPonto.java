package com.estudos.analisecredito.service.strategy;

import com.estudos.analisecredito.domain.Proposta;

public interface CalculoPontuacao {
    int calcular(Proposta proposta);
}
