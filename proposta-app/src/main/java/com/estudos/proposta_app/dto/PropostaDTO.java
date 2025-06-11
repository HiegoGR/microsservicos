package com.estudos.proposta_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropostaDTO {

    private Long id;

    private Double valorSolicitado;

    private int prazoPagamento;

    private boolean aprovada;

    private Boolean integrada;

    private String observacao;

    private Long usuarioId;

}
