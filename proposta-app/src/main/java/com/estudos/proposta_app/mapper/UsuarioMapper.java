package com.estudos.proposta_app.mapper;

import com.estudos.proposta_app.dto.UsuarioDTO;
import com.estudos.proposta_app.entity.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = PropostaMapper.class)
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
    UsuarioDTO toDTO(Usuario entity);
}
