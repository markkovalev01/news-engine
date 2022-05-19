package app.model.mapper;

import app.model.dto.DefaultDto;
import app.model.entities.DefaultEntity;
import lombok.SneakyThrows;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractMapper<E extends DefaultEntity, D extends DefaultDto> implements EntityDtoMapper {

    @Autowired
    ModelMapper modelMapper;

    private Class<E> entityClass;
    private Class<D> dtoClass;

    @PostConstruct
    public void init() {

    }


    @Override
    public E toEntity(DefaultDto dto) {
        return Objects.isNull(dto)
            ? null
            : modelMapper.map(dto, entityClass);
    }

    @Override
    public D toDto(DefaultEntity entity) {
        return Objects.isNull(entity)
            ? null
            : modelMapper.map(entity, dtoClass);
    }

    Converter<E, D> toDtoConverter() {
        return context -> {
            E source = context.getSource();
            D destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    Converter<D, E> toEntityConverter() {
        return context -> {
            D source = context.getSource();
            E destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    Converter<String, LocalDate> stringToDateConverter() {
        return context -> {
            if (Objects.nonNull(context.getSource())) {
                return LocalDate.parse(context.getSource());
            }
            return null;
        };
    }

    Converter<LocalDate, String> datetoStringConverter() {
        return context -> {
            if (Objects.nonNull(context.getSource())) {
                return context.getSource().toString();
            }
            return null;
        };
    }

    protected long convertLocalDateTimeToLong(LocalDateTime dateTime) {
        ZonedDateTime zdt = ZonedDateTime.of(dateTime, ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }


    List<? extends DefaultDto> mapListToDto(List<? extends DefaultEntity> entities,
                                            AbstractMapper<? extends DefaultEntity, ? extends DefaultDto> mapper) {
        return entities
            .stream()
            .map(entity -> mapper.toDto(entity))
            .collect(Collectors.toList());
    }

    List<? extends DefaultEntity> mapListToEntities(List<? extends DefaultDto> dtos,
                                                    AbstractMapper<? extends DefaultEntity, ? extends DefaultDto> mapper) {
        return dtos
            .stream()
            .map(dto -> mapper.toEntity(dto))
            .collect(Collectors.toList());
    }

    void mapSpecificFields(E source, D destination) {
    }

    void mapSpecificFields(D source, E destination) {
    }

}
