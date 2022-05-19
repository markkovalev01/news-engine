package app.services;

import app.model.entities.DefaultEntity;
import app.repositories.DefaultRepository;

import org.springframework.stereotype.Service;

@Service
public interface DefaultService<R extends DefaultRepository, E extends DefaultEntity> {
}
