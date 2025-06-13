package com.estudos.proposta_app.repository;

import com.estudos.proposta_app.entity.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    public List<Proposta> findAllByIntegradaIsFalse();

    @Transactional
    @Modifying
    @Query(value = "update proposta" +
                    "set aprovada = :aprovada," +
                    "observacao = :observacao " +
                    "where id = :id"
          , nativeQuery = true)
    public Proposta atualizarDadosDeProposta(Long id, boolean aprovada, String observacao);
}
