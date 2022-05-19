package app.model.mapper;

import app.model.dto.DefaultDto;
import app.model.entities.DefaultEntity;

public interface EntityDtoMapper<E extends DefaultEntity, D extends DefaultDto> {

    E toEntity(D dto);

    D toDto(E entity);
}
