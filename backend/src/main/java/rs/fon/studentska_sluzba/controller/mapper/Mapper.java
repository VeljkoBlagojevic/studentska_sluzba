package rs.fon.studentska_sluzba.controller.mapper;

import org.springframework.stereotype.Service;

import java.util.List;

public interface Mapper<E, DTO> {

    DTO entityToDTO(E entity);

    E DTOToEntity(DTO dto);

    default List<DTO> entitiesToDTOs(List<E> entities) {
        return entities.stream().map(this::entityToDTO).toList();
    }

    default List<E> DTOsToEntities(List<DTO> dtos) {
        return dtos.stream().map(this::DTOToEntity).toList();
    }

}
