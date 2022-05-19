package app.repositories;

import app.model.entities.JournalEntity;

import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends DefaultRepository<JournalEntity, Long> {

}
