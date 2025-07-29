package com.evoting.evote_backend.mapper;

import com.evoting.evote_backend.dto.ElectionResponseDTO;
import com.evoting.evote_backend.entity.Election;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ElectionMapper {
    ElectionMapper INSTANCE = Mappers.getMapper(ElectionMapper.class);

    ElectionResponseDTO toDto(Election election);
    List<ElectionResponseDTO> toDtoList(List<Election> elections);
}
