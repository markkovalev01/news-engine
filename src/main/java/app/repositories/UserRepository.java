package app.repositories;

import app.model.entities.DefaultEntity;
import app.model.entities.UserEntity;

import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends DefaultRepository<UserEntity, Long>{

    Optional<UserEntity> findOneByLogin(String login);
}
