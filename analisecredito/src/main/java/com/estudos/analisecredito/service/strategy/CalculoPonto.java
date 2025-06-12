package com.estudos.analisecredito.service.strategy;

import com.estudos.analisecredito.domain.Proposta;

public interface CalculoPonto {
    int calcular(Proposta proposta);
}
