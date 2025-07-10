package com.compromissos.gerenciadorcompromissos.mapper;

import com.compromissos.gerenciadorcompromissos.controller.CompromissoDto;
import com.compromissos.gerenciadorcompromissos.entity.CompromissoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CompromissoMapper {

    CompromissoMapper INSTANCE = Mappers.getMapper(CompromissoMapper.class);

    CompromissoEntity toEntity(CompromissoDto dto);
}
