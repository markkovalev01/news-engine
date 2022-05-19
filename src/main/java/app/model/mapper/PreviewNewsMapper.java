package app.model.mapper;

import app.model.dto.NewsViewDTO;
import app.model.dto.PreviewNewsDTO;
import app.model.entities.NewsEntity;
import java.util.Objects;

@Mapper(entity = NewsEntity.class, dto = PreviewNewsDTO.class)
public class PreviewNewsMapper extends AbstractMapper<NewsEntity, PreviewNewsDTO>{


    @Override
    public void init() {
        modelMapper.createTypeMap(NewsEntity.class, PreviewNewsDTO.class)
            .addMappings(m -> {
                m.skip(PreviewNewsDTO::setCreated);
                m.skip(PreviewNewsDTO::setUpdated);
            }).setPostConverter(toDtoConverter());
    }


    @Override
    void mapSpecificFields(NewsEntity source, PreviewNewsDTO destination) {
        destination.setCreated(convertLocalDateTimeToLong(source.getCreated()));
        if (Objects.isNull(source.getUpdatedBy())) {
            destination.setUpdated(0);
        } else {
            destination.setUpdated(convertLocalDateTimeToLong(source.getUpdated()));
        }

    }
}
