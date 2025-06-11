package com.estudos.proposta_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "proposta")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valorSolicitado;

    private int prazoPagamento;

    private boolean aprovada;

    private Boolean integrada;

    private String observacao;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
