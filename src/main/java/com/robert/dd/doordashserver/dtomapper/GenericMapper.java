package com.robert.dd.doordashserver.dtomapper;

import org.mapstruct.MappingTarget;

public interface GenericMapper<E, DTO> {

    DTO mapToDTO(E entity);

    E map(DTO dto);

    E map(DTO dto, @MappingTarget E entity);
}