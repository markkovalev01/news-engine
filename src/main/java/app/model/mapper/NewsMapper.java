package app.model.mapper;

import app.model.dto.NewsDTO;
import app.model.dto.NewsViewDTO;
import app.model.entities.NewsEntity;
import java.util.Objects;

@Mapper(entity = NewsEntity.class, dto = NewsDTO.class)
public class NewsMapper extends AbstractMapper<NewsEntity, NewsDTO>{

    @Override
    public void init() {
        modelMapper.createTypeMap(NewsEntity.class, NewsDTO.class)
            .addMappings(m -> {
                m.skip(NewsDTO::setCreated);
                m.skip(NewsDTO::setUpdated);
            }).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(NewsDTO.class, NewsEntity.class)
            .addMappings(m -> {
                m.skip(NewsEntity::setCreated);
                m.skip(NewsEntity::setUpdated);
            }).setPostConverter(toEntityConverter());
    }


    @Override
    void mapSpecificFields(NewsEntity source, NewsDTO destination) {
        destination.setCreated(convertLocalDateTimeToLong(source.getCreated()));
        if (Objects.isNull(source.getUpdatedBy())) {
            destination.setUpdated(0);
        } else {
            destination.setUpdated(convertLocalDateTimeToLong(source.getUpdated()));
        }
    }
}
