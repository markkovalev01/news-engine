package app.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.exceptions.ResourceNotFoundException;
import app.model.entities.UserEntity;
import app.repositories.UserRepository;

@Service
@Transactional
public class SecurityService {

    @Autowired
    UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserEntity findUserByLogin(String login) {
        return userRepository.findOneByLogin(login).orElseThrow(ResourceNotFoundException::new);
    }
}
