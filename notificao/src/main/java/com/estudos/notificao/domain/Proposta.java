package com.estudos.notificao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proposta {

    private Long id;

    private Double valorSolicitado;

    private int prazoPagamento;

    private boolean aprovada;

    private Boolean integrada;

    private String observacao;

    private Long usuarioId;
}
