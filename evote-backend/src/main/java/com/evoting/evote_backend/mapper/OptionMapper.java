package com.evoting.evote_backend.mapper;

import com.evoting.evote_backend.dto.OptionDTO;
import com.evoting.evote_backend.entity.Option;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OptionMapper {
    List<OptionDTO> toDtoList(List<Option> options);
    OptionDTO toDto(Option option);
}
