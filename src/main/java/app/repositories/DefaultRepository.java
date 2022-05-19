package app.repositories;

import app.model.entities.AbstractEntity;
import app.model.entities.DefaultEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultRepository<T extends AbstractEntity, Long> extends JpaRepository<T, Long> {


}
