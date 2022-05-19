package app.model.entities;

import app.model.dto.DefaultDto;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface DefaultEntity {


    Long getId();

    void setId(Long id);

}
