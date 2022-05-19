package app.services;

import app.model.entities.JournalEntity;
import app.repositories.JournalRepository;

import org.springframework.stereotype.Service;

@Service
public class JournalService extends AbstractService<JournalRepository, JournalEntity> {
}
