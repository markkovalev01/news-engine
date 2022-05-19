package app.model.mapper;

import app.model.dto.NewsViewDTO;
import app.model.entities.NewsEntity;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

import org.springframework.stereotype.Component;

@Mapper(entity = NewsEntity.class, dto = NewsViewDTO.class)
public class NewsViewMapper extends AbstractMapper<NewsEntity, NewsViewDTO> {



    @Override
    public void init() {
        modelMapper.createTypeMap(NewsEntity.class, NewsViewDTO.class)
            .addMappings(m -> {
                m.skip(NewsViewDTO::setCreator);
                m.skip(NewsViewDTO::setUpdater);
                m.skip(NewsViewDTO::setCreated);
                m.skip(NewsViewDTO::setUpdated);
            }).setPostConverter(toDtoConverter());
    }


    @Override
    void mapSpecificFields(NewsEntity source, NewsViewDTO destination) {
        destination.setCreator(source.getCreatedBy().getLogin());
        destination.setCreated(convertLocalDateTimeToLong(source.getCreated()));
        if (Objects.isNull(source.getUpdatedBy())) {
            destination.setUpdater(null);
            destination.setUpdated(0);
        } else {
            destination.setUpdater(source.getUpdatedBy().getLogin());
            destination.setUpdated(convertLocalDateTimeToLong(source.getUpdated()));
        }

    }
}
