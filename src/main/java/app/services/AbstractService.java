package app.services;

import app.exceptions.ConflictException;
import app.exceptions.ResourceNotFoundException;
import app.model.entities.AbstractEntity;
import app.model.entities.DefaultEntity;
import app.repositories.DefaultRepository;
import app.utils.EntityUtil;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public abstract class AbstractService<R extends DefaultRepository<E, Long>, E extends AbstractEntity>
    implements DefaultService {

    @Autowired
    R repository;

    E save(E entity) {
        return repository.save(entity);
    }

    @Transactional(readOnly = true)
    @SneakyThrows
    public E get(Long id) {
        return repository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public List<E> getAll() {
        return repository.findAll();
    }

    public E create(E entity) {
        if (Objects.nonNull(entity.getId()) && repository.existsById(entity.getId())) {
            throw new ConflictException(
                String.format("Сущность %s с id: %s уже существует!", entity.getClass().getName(), entity.getId()));
        }
        return save(entity);
    }

    public E update(E entity) {
        if (Objects.isNull(entity.getId()) || !repository.existsById(entity.getId())) {
            throw new ConflictException(
                String.format("Сущность %s не существует!", entity.getClass().getName(), entity.getId()));
        }
        return save(EntityUtil.copy(get(entity.getId()), entity));
    }

    @Transactional
    public List<E> delete(Long id) {
        repository.delete(get(id));
        return getAll();
    }
}
