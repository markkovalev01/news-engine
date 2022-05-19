package app.services;

import app.exceptions.ResourceNotFoundException;
import app.model.entities.UserEntity;
import app.repositories.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<UserRepository, UserEntity>{


    public UserEntity getByLogin(String login){
        return repository.findOneByLogin(login).orElseThrow(ResourceNotFoundException::new);
    }
}
