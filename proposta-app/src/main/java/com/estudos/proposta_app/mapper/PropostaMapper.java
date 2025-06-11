package com.estudos.proposta_app.mapper;

import com.estudos.proposta_app.dto.PropostaDTO;
import com.estudos.proposta_app.entity.Proposta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.text.NumberFormat;

@Mapper(componentModel = "spring")
public interface PropostaMapper {

    @Mapping(source = "usuarioId", target = "usuario.id")
    Proposta toEntity(PropostaDTO dto);

    @Mapping(source = "usuario.id", target = "usuarioId")
    PropostaDTO toDTO(Proposta entity);
}

